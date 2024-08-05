$(document).ready(function() {
  const token = Cookies.get('Authorization');
  console.log('Authorization token:', token);

  if (token) {
    $('#login-logout-link').text('로그아웃').attr('onclick', 'logout()');
  } else {
    $('#login-logout-link').text('로그인').attr('onclick', "location.href='login-page'");
  }

  // Logout function
  window.logout = function() {
    Cookies.remove('Authorization', { path: '/' });
    alert('로그아웃 되었습니다.');
    location.href = '/login-page';
  };

  // Search functionality
  document.querySelector('.search-bar button').addEventListener('click', function() {
    const searchQuery = document.querySelector('.search-bar input').value;
    const searchType = document.querySelector('.search-type-select').value;
    alert(`Searching for: "${searchQuery}" in ${searchType}`);
    // Here you would typically send the search query and type to a server
  });

  document.querySelector('.search-bar input').addEventListener('keypress', function(e) {
    if (e.key === 'Enter') {
      const searchQuery = this.value;
      const searchType = document.querySelector('.search-type-select').value;
      alert(`Searching for: "${searchQuery}" in ${searchType}`);
      // Here you would typically send the search query and type to a server
    }
  });

  // Add hover effect to main category links
  const mainCategoryLinks = document.querySelectorAll('.main-categories a');
  mainCategoryLinks.forEach(link => {
    link.addEventListener('mouseover', function() {
      this.style.color = '#4CAF50';
    });
    link.addEventListener('mouseout', function() {
      this.style.color = '#333';
    });
  });
});

// 왼쪽 메뉴바
$('#toggle').click(function() {
  $('#toggle .bar').toggleClass('animate');
  $('#page').toggleClass('overlay');
});

// 가운데 상단바
console.clear();

const { gsap } = window;

let currentLink;
let currentIndex = 0;
const links = document.querySelectorAll("nav a");

links.forEach((link, index) => {
  link.addEventListener("click", (e) => {
    addActive(e, index);
  });
});

function addActive(e, i) {
  const target = e.currentTarget;
  if (target != currentLink) {
    let direction;
    if (currentIndex < i) direction = "right";
    else direction = "left";
    const slide = target.querySelector(".slide");

    gsap.timeline()
    .call(() => {
      links.forEach((link) => {
        link.classList.remove("active");
      });
    })
    .fromTo(
        slide,
        {
          transformOrigin: `${direction == "left" ? "right" : "left"} center`,
          scaleX: 0,
        },
        {
          delay: 0.1,
          duration: 0.25,
          scaleX: 1,
        }
    )
    .call(() => {
      target.classList.add("active");
    })
    .to(slide, {
      delay: 0.25,
      duration: 0.25,
      transformOrigin: `${direction} center`,
      scaleX: 0,
    });
    currentLink = target;
    currentIndex = i;
  }
}