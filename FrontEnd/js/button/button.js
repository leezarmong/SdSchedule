/**
 * 
 */
document.querySelectorAll('.explore-menu').forEach(button => {

const bounding = button.getBoundingClientRect();

button.addEventListener('mousemove', e => {

  let dy = (e.clientY - bounding.top - bounding.height / 2) / -1;
  let dx = (e.clientX - bounding.left - bounding.width / 2) / 10;

  dy = dy > 10 ? 10 : dy < -10 ? -10 : dy;
  dx = dx > 8 ? 8 : dx < -8 ? -8 : dx;

  button.style.setProperty('--rx', dy + 'deg');
  button.style.setProperty('--ry', dx + 'deg');

});

button.addEventListener('mouseleave', e => {

  button.style.setProperty('--rx', '0deg');
  button.style.setProperty('--ry', '0deg');

});

});
//# sourceURL=pen.js
  
