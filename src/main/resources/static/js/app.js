let inputText = document.querySelectorAll(".input-text");
let warningMessage = document.querySelectorAll(".message");
let submitBtn = document.querySelector(".btn-submit");
let popup = {
  timer: 500,
  alert: function (txt) {
    this.open("type-alert", txt);
  },
};

function emailCheck() {
  if (
    inputText[0].value == "" ||
    !inputText[0].value.includes("@") ||
    !inputText[0].value.includes(".com")
  ) {
    warningMessage[0].style.visibility = "visible";
    inputText[0].style.borderColor = "red";
  } else {
    submitBtn.setAttribute("disabled", "true");
    document.querySelector(".email-form").submit();
  }
}

function passwordCheck() {
  if (
    inputText[0].value == "" ||
    !inputText[0].value.includes("@") ||
    !inputText[0].value.includes(".com")
  ) {
    warningMessage[0].style.visibility = "visible";
    inputText[0].style.borderColor = "red";
    if (inputText[1].value == "") {
      warningMessage[1].style.visibility = "visible";
      inputText[1].style.borderColor = "red";
    } else {
      warningMessage[1].style.visibility = "hidden";
      inputText[1].style.borderColor = "#cacaca";
    }
  } else if (inputText[1].value == "") {
    warningMessage[0].style.visibility = "hidden";
    inputText[0].style.borderColor = "#cacaca";
    warningMessage[1].style.visibility = "visible";
    inputText[1].style.borderColor = "red";
  } else {
    submitBtn.setAttribute("disabled", "true");
    document.querySelector(".email-form").submit();
  }
}

function nameCheck() {
  if (inputText[2].value == "") {
    warningMessage[2].style.visibility = "visible";
    inputText[2].style.borderColor = "red";
    if (inputText[1].value == "") {
      warningMessage[1].style.visibility = "visible";
      inputText[1].style.borderColor = "red";
    } else {
      warningMessage[1].style.visibility = "hidden";
      inputText[1].style.borderColor = "#cacaca";
    }
  } else if (inputText[1].value == "") {
    warningMessage[2].style.visibility = "hidden";
    inputText[2].style.borderColor = "#cacaca";
    warningMessage[1].style.visibility = "visible";
    inputText[1].style.borderColor = "red";
  } else {
    submitBtn.setAttribute("disabled", "true");
    document.querySelector(".email-form").submit();
  }
}

function updateCheck() {
  submitBtn.setAttribute("disabled", "true");
  document.querySelector(".email-form").submit();
}
