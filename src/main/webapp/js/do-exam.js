// Ngăn chặn sự kiện chuột phải
document.addEventListener("contextmenu", function(e) {
	e.preventDefault();
});

document.addEventListener("click", () => {
	// Nếu đang không full màn hình thì trả về null
	if (!document.fullscreenElement) {
		document.querySelector('.main-content').style.border = '2px solid red';
		document.documentElement.requestFullscreen().catch((e) => {
			console.log(e);
		});
	}

	document.addEventListener("fullscreenchange", function() {
		if (document.fullscreenElement) {
			document.querySelector('.main-content').style.height = 'calc(93vh + 17px)';
		}else {
			document.querySelector('.main-content').style.height = 'calc(93vh + 5px)';
		}
	});
});

document.addEventListener('keydown', function(event) {
	event.preventDefault();
});

let time = startingMinutes * 60;
const timeoutElement = document.getElementById('timeout');
const timeDoExamElement = document.getElementById('examTimeout');

setInterval(updateTimeout, 1000);
function updateTimeout() {
	const minutes = Math.floor(time / 60);
	let seconds = time % 60;
	timeoutElement.innerHTML = String(minutes).padStart(2, '0') + ':' + String(seconds).padStart(2, '0');
	timeDoExamElement.value = timeoutElement.innerHTML;
	if (time <= 0) {
		document.forms[0].submit();
	}
	time--;
}
var selectedAnswers = {};
function updateSelectedAnswers(questionId, answerValue, type, id, className) {
	if (!selectedAnswers.hasOwnProperty(questionId)) {
		selectedAnswers[questionId] = [];
	}
	if (type === 'checkbox') {
		var answerIndex = selectedAnswers[questionId].indexOf(answerValue);
		if (answerIndex !== -1) {
			selectedAnswers[questionId].splice(answerIndex, 1);
		} else {
			selectedAnswers[questionId].push(answerValue);
		}
		const divElement = document.getElementById(id);
		if (divElement.classList.contains("choose")) {
			divElement.classList.remove("choose");
		}
		else {
			divElement.classList.add("choose");
		}
	} else {
		selectedAnswers[questionId] = [answerValue];
		const divElements = document.getElementsByClassName(className);
		Array.from(divElements).forEach(function(element) {
			element.classList.remove('choose');
		});
		const divElement = document.getElementById(id).closest("div");
		divElement.classList.add("choose");
	}
	const answers = document.getElementById('selected-answers');
	answers.value = JSON.stringify(selectedAnswers);
}