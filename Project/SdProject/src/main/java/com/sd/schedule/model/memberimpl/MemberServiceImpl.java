package com.sd.schedule.model.memberimpl;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sd.schedule.model.member.MemberService;
import com.sd.schedule.model.member.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberMapper memberMapper;

	
	//멤버 리스트
	@Override
	public List<MemberVO> memberList (String user_id){
		return memberMapper.memberList(user_id);
	}
	
	//멤버 리스트(페이지)
	@Override
	public List<MemberVO> getMemberList(MemberVO vo, int start, int end){
		return memberMapper.getMemberList(vo, start, end);
	}
	

	//멤버 입력
	@Override
	public void insertMember(MemberVO vo) {
		memberMapper.insertMember(vo);
	}
	
	//멤버 삭제
	@Override
	public void deleteMember(int member_no) {
		memberMapper.deleteMember(member_no);
	}
	
	//멤버 수 카운트
	@Override
	public int countMember (MemberVO vo) {
		return memberMapper.countMember(vo);
	}
	
	//이름 중복 검사
//	@Override
//	public int nameCount (MemberVO vo) {
//		return memberMapper.nameCount(vo);
//		}
	// Service class에서 해당 메소드 추가
	@Override
	public int countMemberByNameAndUserId(String member_name, String user_id) {
	    return memberMapper.countMemberByNameAndUserId(member_name, user_id);
	}

	
	//멤버 수 불러오기(검색)
	@Override
	public int countSearch(String name, String user_id) {
	    return memberMapper.countSearch(name, user_id);
	}
	
	//멤버 검색
	@Override
	public List<MemberVO> searchMember (String name, int start, int end, String user_id){
		
		return memberMapper.searchMember(name, start, end, user_id);
	}
	
	//멤버 업데이트
	@Override
	public void updateMember (MemberVO vo) {
		memberMapper.updateMember(vo);
	}
	
	//엑셀 데이터 추출
	@Override								// MultipartFile = 파일 업로드 클래스
	public List<String> processExcelFile(MultipartFile file, String user_id) throws IOException {
	    List<String> excelMemberNames = new ArrayList<>();  // 엑셀에서 추출된 멤버 이름 리스트
	    List<String> membersToDeleteNames = new ArrayList<>();  // 삭제해야 할 멤버 이름 리스트

	    try (InputStream inputStream = file.getInputStream(); Workbook workbook = new XSSFWorkbook(inputStream)) {
    															// 'Workbook'은 Excel 파일을 포함한 Microsoft 문서용 Java API인 Apache POI 라이브러리의 클래스
	        Sheet sheet = workbook.getSheetAt(0);  // 첫 번째 시트를 가져옴
	        if (sheet == null) {
	            throw new IOException("Sheet not found");
	        }

	        // A 열에서 이름을 읽음 (A7, A9, A11, ..., A43)
	        for (int i = 6; i <= 47; i += 2) {
	            Row row = sheet.getRow(i);	// 행
	            if (row != null) {
	                Cell cell = row.getCell(0);  // A 열
	                if (cell != null) {
	                    String name = getCellValueAsString(cell).split("\\s+")[0]; // 첫 번째 공백 전까지의 문자열만 추출
	                    if (!name.isEmpty()) {
	                        excelMemberNames.add(name);
	                    }
	                }
	            }
	        }

	        // L 열에서 이름을 읽음 (L3, L5, L7, ..., L43)
	        for (int i = 2; i <= 47; i += 2) {
	            Row row = sheet.getRow(i);	// 행
	            if (row != null) {
	                Cell cell = row.getCell(11);  // L 열
	                if (cell != null) {
	                    String name = getCellValueAsString(cell).split("\\s+")[0]; // 첫 번째 공백 전까지의 문자열만 추출
	                    if (!name.isEmpty()) {
	                        excelMemberNames.add(name);
	                    }
	                }
	            }
	        }
	    }

	    // 엑셀에서 추출한 멤버 이름 출력
	    System.out.println("엑셀에서 추출한 멤버 이름: " + excelMemberNames);

	    List<MemberVO> allMembers = memberMapper.memberList(user_id);  // DB에서 가져온 이전 멤버들
	    List<MemberVO> membersToDelete = new ArrayList<>();  // 삭제해야 할 멤버 리스트

	    // DB에서 가져온 멤버 이름 출력
	    System.out.println("DB에서 가져온 멤버 이름: ");
	    for (MemberVO member : allMembers) {
	        System.out.println(member.getMember_name());
	        if (!excelMemberNames.contains(member.getMember_name())) {  // 엑셀에 없는 멤버는 삭제 대상
	            membersToDelete.add(member);
	            membersToDeleteNames.add(member.getMember_name());
	        }
	    }

	    // 삭제해야 할 멤버 리스트 출력
	    System.out.println("삭제해야 할 멤버 이름: ");
	    for (MemberVO member : membersToDelete) {
	        System.out.println(member.getMember_name());
	    }

	    if (!membersToDelete.isEmpty()) {  // 삭제해야 할 멤버 리스트가 비어있지 않다면 삭제
	        memberMapper.deleteMembers(membersToDelete);
	    }

	    return membersToDeleteNames;
	}

	
	//numeric 오류 대비 String으로 변환 메소드
	private String getCellValueAsString(Cell cell) {
	    switch (cell.getCellType()) {
	        case STRING:
	            return cell.getStringCellValue();
	        case NUMERIC:
	            if (DateUtil.isCellDateFormatted(cell)) {	// 날짜 형식이면 날짜 값을 문자열 반환
	                return cell.getDateCellValue().toString();
	            } else {
	                return String.valueOf((int) cell.getNumericCellValue());
	            }
	        case BOOLEAN:
	            return String.valueOf(cell.getBooleanCellValue());
	        case FORMULA:
	            // 수식 셀 처리: 수식의 결과 값을 가져옴
	            Workbook wb = cell.getSheet().getWorkbook();
	            CreationHelper creationHelper = wb.getCreationHelper();
	            FormulaEvaluator evaluator = creationHelper.createFormulaEvaluator();
	            CellValue cellValue = evaluator.evaluate(cell);
	            switch (cellValue.getCellType()) {
	                case STRING:
	                    return cellValue.getStringValue();
	                case NUMERIC:
	                    return String.valueOf((int) cellValue.getNumberValue());
	                case BOOLEAN:
	                    return String.valueOf(cellValue.getBooleanValue());
	                case BLANK:		// 빈 셀 빈 문자열 반환
	                    return "";
	                default:
	                    return "";
	            }
	        case BLANK:
	            return "";
	        default:
	            return "";
	    }
	}

	}


