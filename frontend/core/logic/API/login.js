function formInputs(direction) {
  var signInForm = document.getElementById('signIn');
  signInForm.addEventListener('submit', function (event) {
    event.preventDefault();
    var formData = new FormData(signInForm);
    var formInputs = {};
    formData.forEach(function (value, key) {
      formInputs[key] = value;
      console.log("key: ".concat(key, " \nvalue: ").concat(value));
    });
    RequestData(formInputs, direction);
  });
}
function RequestData(formObject, direction) {
  fetch(direction, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(formObject)
  })
    .then(function (response) {
      if (!response.ok) {
        throw new Error("Server error: ".concat(response.status));
      }
      return response.json();
    })
    .then(function (APIResponse) {
      console.log('Success! Data received:', APIResponse.token);
    })
    .catch(function (error) {
      console.error('Error!', error);
    });
}
document.addEventListener('DOMContentLoaded', formInputs('http://localhost:8080/signin'));
