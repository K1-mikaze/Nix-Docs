export { };

function formInputs(direction: string): any {
  let signInForm: any = document.getElementById('signIn');

  signInForm.addEventListener('submit', (event: HTMLFormElement) => {
    event.preventDefault();

    const formData: FormData = new FormData(signInForm);

    let formInputs = {};
    formData.forEach((value, key) => {
      formInputs[key] = value;
      console.log(`key: ${key} \nvalue: ${value}`)
    });

    RequestData(formInputs, direction);
  });
}


function RequestData(formObject, direction: string) {
  fetch(direction, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(formObject)
  })
    .then(response => {
      if (!response.ok) {
        throw new Error(`Server error: ${response.status}`);
      }
      return response.json();
    })
    .then(APIResponse => {
      console.log('Success! Data received:', APIResponse.token);
    })
    .catch(error => {
      console.error('Error!', error);
    });
}


document.addEventListener('DOMContentLoaded', formInputs('http://localhost:3000/greet'));
