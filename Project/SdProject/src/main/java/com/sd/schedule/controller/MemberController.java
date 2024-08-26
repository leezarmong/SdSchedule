package com.sd.schedule.controller;

import java.io.IOException;



import java.util.HashMap;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sd.schedule.model.member.MemberService;
import com.sd.schedule.model.member.MemberVO;
import com.sd.schedule.model.station.StationService;
import com.sd.schedule.model.station.StationVO;
import com.sd.schedule.model.user.UserVO;
import com.sd.schedule.pager.Pager;

import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {

	private static final Logger log = LoggerFactory.getLogger(MemberController.class);
	
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	StationService stationService;

	// 멤버 리스트
	@GetMapping("/memberpage")
	public String memberpage(@RequestParam(defaultValue = "1") int curPage, MemberVO vo, Model model,
	                         HttpSession session) {

	    // Retrieve the user_id from session
	    UserVO user = (UserVO) session.getAttribute("user");
	    String user_id = user.getUser_id();
	    vo.setUser_id(user_id);
	    
	    // Get the count of members
	    int count = memberService.countMember(vo);
	    Pager pager = new Pager(count, curPage);
	    int start = pager.getPageBegin();
	    int end = pager.getPageEnd();

	    model.addAttribute("count", count);
	    
	    // Retrieve the member list
	    List<MemberVO> list = memberService.getMemberList(vo, start, end);
	    
	    // Prepare the data to be passed to the view
	    HashMap<String, Object> map = new HashMap<>();
	    map.put("list", list);
	    map.put("count", count);
	    map.put("pager", pager);
	    model.addAttribute("map", map);

	    return "member/memberpage";
	}

	// 멤버 추가
	@PostMapping("/insert")
	public String insert(MemberVO vo, StationVO svo) {
		
		//직원 
		memberService.insertMember(vo);
		
		//station
		stationService.insertStation(svo);
		return "redirect:memberpage";
	}

	// 멤버 중복 확인
//	@ResponseBody
//	@PostMapping("/check")
//	public int checkID(MemberVO vo) {
//		int result = memberService.nameCount(vo);
//		return result;
//	}
	@ResponseBody
	@PostMapping("/check")
	public ResponseEntity<Integer> checkMember(@RequestParam String member_name, @RequestParam String user_id) {
		int count = memberService.countMemberByNameAndUserId(member_name, user_id);
		return new ResponseEntity<>(count, HttpStatus.OK);
		// HttpStatus.OK = 중복된 멤버가 존재하지 않을경우 OK 상태 코드를 반환 .
		// ResponseEntity<> = Spring의 클래스로, HTTP 응답을 나타낸다.
	}

	// 멤버 삭제
	@PostMapping("/delete")
	public String delete(String member_name) {

		System.out.println(member_name);
		
		stationService.deleteStation(member_name);
		
		memberService.deleteMember(member_name);
		
		return "redirect:memberpage";
	}

	// 멤버 검색 후 페이지
	@GetMapping("/searchMember")
	public String searchMember(Model model, HttpSession session, String name,
			@RequestParam(defaultValue = "1") int curPage) {

		UserVO user = (UserVO) session.getAttribute("user");
		String user_id = user.getUser_id();

		int count = memberService.countSearch(name, user_id);
//		System.out.print(count);

		Pager pager = new Pager(count, curPage);
		int start = pager.getPageBegin();
		int end = pager.getPageEnd();

		session.setAttribute("name", name); // 아이디 검색
		session.setAttribute("curPage", curPage);

		List<MemberVO> list = memberService.searchMember(name, start, end, user_id);
//	        System.out.print(user_id);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("count", count);
		map.put("pager", pager);
		map.put("name", name);
		model.addAttribute("map", map);

		return "member/searchmember";
	}

	// 멤버 업데이트
	@GetMapping("/updateMember")
	public String updateMember(MemberVO vo, StationVO svo) {
		memberService.updateMember(vo);
		stationService.updateStation(svo);
		return "redirect:memberpage";
	}

	// 멤버 리뉴얼
	@GetMapping("/renewal")
	public String renewal() {
		return "member/renewal";
	}

	// 멤버 퇴사자 전체 삭제
	@PostMapping("/removeEmployee")
	public ResponseEntity<List<String>> removeEmployee(@RequestParam("file") MultipartFile file, HttpSession session,
			MemberVO vo) {
		try {
			UserVO user = (UserVO) session.getAttribute("user");
			String user_id = user.getUser_id();
			vo.setUser_id(user_id);
			List<String> membersToDelete = memberService.deleteMembersFromExel(file, user_id);
			return ResponseEntity.ok(membersToDelete);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			/*
			 * 이 코드는 예외 처리 시 서버에서 문제가 발생했음을 클라이언트에게 알리는 역할을 합니다. 예를 들어, 파일 처리 중에
			 * IOException이 발생했을 때, 클라이언트는 500 상태 코드를 통해 서버에서 문제가 발생했음을 알 수 있습니다.
			 */
		}
	}

	// 멤버 추가 전체
	@PostMapping("/addrenewal")
	public String addMembersFromExel(@RequestParam("file") MultipartFile file, Model model, HttpSession session, MemberVO vo) throws IOException {

		UserVO user = (UserVO) session.getAttribute("user");
		String user_id = user.getUser_id();
		vo.setUser_id(user_id);
		

		//추가해야될 리스트 가져오기
		List<String> membersToAdd = memberService.addMembersFromExel(file, user_id);
		model.addAttribute("membersToAdd", membersToAdd);
		
		// List 추가 인원 
		int count = membersToAdd.size();
		model.addAttribute("count",count);

		return "member/addrenewal";
	}
	
	// //새로운 신규인원 새로 추가
	@PostMapping("/addMembersInsert")
	public String addMembersInsert(@RequestBody List<MemberVO> members) {
	    try {
	    	
	    	/*
	    	 * 
	    	 * 애초에 js 에서 members 배열 형식으로 들어오기때문에 때어내기 힘든 부분이 있었음.
	    	 * members 배열로 만든 이유는 front 에서 팝업 형식으로 출력한 이름 에 관련된 데이터를 addMembersInsert 이라는 경로로
	    	 * 쉽게 JSON 형식으로 가져와 back 과 연결하기 위해서 였음.
	    	 * 
	    	 * 이문제를 해결하기위해 배열에서 하나씩 꺼내오는 형식을사용 하기로 함.
	    	 * 
	    	 * */
	    	if(members != null && !members.isEmpty()) {
	    		// members 파라미터 가 널이 아니거나 비어있지 않은 경우 if 를 실행.
	    		
	    		MemberVO firstMember = members.get(0);
	    		//파라미터로 들어오게된 members 를 첫번째 인덱스로 지정후 memberVO 를 인스턴스 한다.
	    		
	    		StationVO svo = new StationVO();
	    		 svo.setFrei(firstMember.getFrei());
	             svo.setGrill(firstMember.getGrill());
	             svo.setMake(firstMember.getMake());
	             svo.setExpo(firstMember.getExpo());
	             svo.setDish(firstMember.getDish());
	             svo.setUser_id(firstMember.getUser_id());
	             svo.setMember_name(firstMember.getMember_name());
	    		
	           //station
	 			stationService.insertStation(svo);
	    		
	 			
	 			
	 			for (MemberVO member : members) {
	                member.setFrei(null);
	                member.setGrill(null);
	                member.setMake(null);
	                member.setExpo(null);
	                member.setDish(null);
	                member.setMember_name(member.getMember_name());  
	            }
	 		
	 			//memberInsert
	        memberService.insertMembers(members);
	    	}
			
			
	        log.info("Received members for insertion: {}", members);
	    } catch (Exception e) {
	        log.error("Error inserting members", e);
	        return "error"; // 에러 페이지로 리디렉션할 수도 있습니다.
	    }
	    return "redirect:/memberpage";
	}


	// 인원 추가하기 만들까..?
//	 @GetMapping("/items")
//	    public String getItems(Model model) {
//	        List<Item> items = new ArrayList<>();
//	        items.add(new Item("Item1", 10));
//	        items.add(new Item("Item2", 20));
//	        items.add(new Item("Item3", 30));
//
//	        model.addAttribute("items", items);
//	        return "items";
//	    }

}
