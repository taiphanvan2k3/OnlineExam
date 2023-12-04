window.addEventListener('DOMContentLoaded', function() {
	const textAreaElement = document.querySelectorAll('textarea');
	textAreaElement.forEach((element) => {
		element.addEventListener('input', function() {
			// Tính toán chiều cao dựa trên số hàng và lineHeight
			const lineHeight = parseInt(getComputedStyle(this).lineHeight);
			const rows = this.value.split('\n').length;
			const newHeight = rows * lineHeight + 'px';

			// Cập nhật số hàng và chiều cao
			this.setAttribute('rows', rows);
			this.style.height = newHeight;
		});
	});

	const radioElements = document.querySelectorAll('.div-types input[type="radio"]');
	radioElements.forEach((radioElement) => {
		radioElement.addEventListener('change', function() {
			if (radioElement.checked) {
				if (radioElement.value === 'single') {
					document.querySelector('.div-single').style.display = 'inline-block';
					document.querySelector('.div-multiple').style.display = 'none';
				} else {
					document.querySelector('.div-single').style.display = 'none';
					document.querySelector('.div-multiple').style.display = 'inline-block';
				}
			}
		})
	});
});