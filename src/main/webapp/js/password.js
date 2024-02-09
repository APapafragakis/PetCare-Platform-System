const passwordInput = document.getElementById('passwordInput');
const confirmPasswordInput = document.getElementById('confirmPassword');
const passwordMismatchMessage = document.getElementById('passwordMismatchMessage');
const showPasswordButton = document.getElementById('showPasswordButton');
const passwordStrengthMessage = document.getElementById("passwordStrengthMessage");
const submitButton = document.getElementById("submitButton");
passwordInput.addEventListener('input', () => {
    passwordMismatchMessage.innerHTML = '';
});

confirmPasswordInput.addEventListener('input', () => {
    if (passwordInput.value !== confirmPasswordInput.value) {
        passwordMismatchMessage.innerHTML = 'Passwords do not match.';
        e.preventDefault();
    }else{
        passwordMismatchMessage.innerHTML = '';
    }
});

showPasswordButton.addEventListener('click', () => {
    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
    } else {
        passwordInput.type = 'password';
    }
});

document.querySelector('form').addEventListener('submit', (e) => {
    
});

passwordInput.addEventListener("input", function () {
    const password = this.value;

    const forbiddenSequences = ["cat", "dog", "gata", "skulos"];
    let containsForbiddenSequence = false;
    for (const sequence of forbiddenSequences) {
        if (password.includes(sequence)) {
            containsForbiddenSequence = true;
            break;
        }
    }

    let passwordStrength = "medium";
    let count = 0;
    for(let character of password){
        if(character >= '0' && character <= '9'){
            count ++;
        }
    }
    if(password.length/2 <= count){
        passwordStrength = "weak";
    }

    if (containsForbiddenSequence) {
        passwordStrength = "weak";
    } else if (/[A-Z]/.test(password) && /[a-z]/.test(password) && /\d/.test(password) && /[!@#$%^&*]/.test(password)) {
        passwordStrength = "strong";
    }

    if (passwordStrength === "weak") {
        passwordStrengthMessage.innerHTML = "Weak Password";
        passwordStrengthMessage.style.color = "red";
    } else if (passwordStrength === "strong") {
        passwordStrengthMessage.innerHTML = "Strong Password";
        passwordStrengthMessage.style.color = "green";
    } else {
        passwordStrengthMessage.innerHTML = "Medium Password";
        passwordStrengthMessage.style.color = "orange";
    }

    if (passwordStrength === "weak") {
        submitButton.disabled = true;
    } else {
        submitButton.disabled = false;
    }
});
 


