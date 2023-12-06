function checkCanEnableButtons(currentPage, maxPage) {
	const btnNext = document.querySelector('.pagination .next button');
	const btnPrevious = document.querySelector('.pagination .prev button');

	if (currentPage === undefined) {
		const currentPageInput = document.querySelector('input[name="current-page"]');
		currentPage = Number.parseInt(currentPageInput.value);
	}

	if (maxPage === undefined) {
		maxPage = document.querySelector('input.total-pages').value;
	}

	if (currentPage == maxPage) {
		btnNext.disabled = true;
	} else {
		btnNext.disabled = false;
	}

	if (currentPage == 1) {
		btnPrevious.disabled = true;
	} else {
		btnPrevious.disabled = false;
	}
}

function updateCurrentPage(action) {
	const form = document.querySelector('.div-pagination');
	const currentPageInput = document.querySelector('input[name="current-page"]');
	let currentPage = Number.parseInt(currentPageInput.value);
	const maxPage = document.querySelector('input.total-pages').value;

	if (action === 'next') {
		currentPage++;
	} else {
		currentPage--;
	}
	currentPageInput.value = currentPage;
	checkCanEnableButtons(currentPage, maxPage);
	form.submit();
}

document.addEventListener('DOMContentLoaded', function() {
	const btnNext = document.querySelector('.pagination .next button');
	const btnPrevious = document.querySelector('.pagination .prev button');

	checkCanEnableButtons();

	btnNext.addEventListener('click', function() {
		updateCurrentPage('next');
	});

	btnPrevious.addEventListener('click', function() {
		updateCurrentPage('previous');
	});
});