(function($) {
	$('.join').on('click', function() {
		$('.form-join').css('display', 'block');
		$('.form-login').css('display', 'none');
		$('.bg-container').css('display', 'block');
	})

	$('.login').on('click', function() {
		$('.form-login').css('display', 'block');
		$('.form-join').css('display', 'none');
		$('.bg-container').css('display', 'block');
	})

	$('.bg-container').on('click', function() {
		$('.form-login').css('display', 'none');
		$('.form-join').css('display', 'none');
		$('.bg-container').css('display', 'none');
	})

	$('.go-login').on('click', function() {
		$('.form-login').css('display', 'block');
		$('.form-join').css('display', 'none');
		$('.bg-container').css('display', 'block');
	})
	$('.go-join').on('click', function() {
		$('.form-join').css('display', 'block');
		$('.form-login').css('display', 'none');
		$('.bg-container').css('display', 'block');
	})
	
	window.goToStep = function(step) {
		const $steps = $('.form-container');
		$steps.each(function(index) {
			if (index + 1 === step) {
				$(this).removeClass('hidden');
			} else {
				$(this).addClass('hidden');
			}
		});
	};
	
	$('.submit-btn').on('click',function() {
		if($('#id') === "" || $('pw') === ""){
			alert('값을 입력해주세요')
		}
	})
});

function showSection(sectionId) {
	const sections = document.querySelectorAll('.content-section');
	sections.forEach(section => section.classList.remove('active'));
	document.getElementById(sectionId).classList.add('active');
}

/* 스마트도서관 정보 */
document.addEventListener('DOMContentLoaded', function() {
	const paths = document.querySelectorAll('.map-svg path');
	const tooltip = document.querySelector('.map-tooltip');

	const guNames = {
		'donggu': '동구',
		'seogu': '서구',
		'namgu': '남구',
		'bukgu': '북구',
		'gwangsangu': '광산구'
	};

	paths.forEach(path => {
		path.addEventListener('mousemove', (e) => {
			const guId = path.parentElement.id;
			tooltip.textContent = `${guNames[guId]}`;
			tooltip.style.visibility = 'visible';
			tooltip.style.left = e.pageX + 10 + 'px';
			tooltip.style.top = e.pageY + 10 + 'px';
		});

		path.addEventListener('mouseleave', () => {
			tooltip.style.visibility = 'hidden';
		});
	});
});