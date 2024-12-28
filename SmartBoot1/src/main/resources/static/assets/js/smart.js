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
	
	

})(jQuery);

function showSection(sectionId) {
	const sections = document.querySelectorAll('.content-section');
	sections.forEach(section => section.classList.remove('active'));
	document.getElementById(sectionId).classList.add('active');
}

const prevButton = document.querySelector('.carousel .prev');
const nextButton = document.querySelector('.carousel .next');
const images = document.querySelectorAll('.carousel img');
let currentIndex = 0;
