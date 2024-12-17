let users = {};
let currentUser = null;
let balance = 1000;
let pin = 1234;
let transactionHistory = [];

function signUp() {
    const username = document.getElementById('signup-username').value;
    const password = document.getElementById('signup-password').value;
    const confirmPassword = document.getElementById('signup-confirm-password').value;

    if (!username || !password) {
        alert('Please fill in all fields.');
        return;
    }

    if (password !== confirmPassword) {
        alert('Passwords do not match. Please try again.');
        return;
    }

    if (users[username]) {
        alert('Username already exists. Please choose a different username.');
        return;
    }

    users[username] = { password: password, pin: pin };
    alert('Sign up successful! Please log in.');
    document.getElementById('signup-section').style.display = 'none';
    document.getElementById('login-section').style.display = 'block';
}

function login() {
    const username = document.getElementById('login-username').value;
    const password = document.getElementById('login-password').value;

    if (users[username] && users[username].password === password) {
        currentUser = username;
        alert(`Login successful! Welcome, ${username}`);
        document.getElementById('login-section').style.display = 'none';
        document.getElementById('atm-section').style.display = 'block';
    } else {
        alert('Invalid username or password. Please try again.');
    }
}

function logout() {
    currentUser = null;
    document.getElementById('atm-section').style.display = 'none';
    document.getElementById('login-section').style.display = 'block';
}

function showBalance() {
    document.getElementById('atm-output').textContent = `Your current balance is: $${balance.toFixed(2)}`;
    transactionHistory.push(`Checked balance: $${balance.toFixed(2)}`);
}

function depositMoney() {
    const amount = parseFloat(prompt('Enter the amount to deposit:'));
    if (amount > 0) {
        balance += amount;
        document.getElementById('atm-output').textContent = `Deposited $${amount.toFixed(2)} successfully.`;
        transactionHistory.push(`Deposited: $${amount.toFixed(2)}`);
    } else {
        alert('Invalid amount. Deposit failed.');
    }
}

function withdrawMoney() {
    const enteredPin = prompt('Enter your PIN to withdraw money:');
    if (parseInt(enteredPin) !== pin) {
        alert('Incorrect PIN! Withdrawal denied.');
        return;
    }

    const amount = parseFloat(prompt('Enter the amount to withdraw:'));
    if (amount > 0 && amount <= balance) {
        balance -= amount;
        document.getElementById('atm-output').textContent = `Withdrew $${amount.toFixed(2)} successfully.`;
        transactionHistory.push(`Withdrew: $${amount.toFixed(2)}`);
    } else {
        alert('Invalid amount or insufficient funds.');
    }
}

function changePin() {
    const oldPin = prompt('Enter your current PIN:');
    if (parseInt(oldPin) === pin) {
        const newPin = prompt('Enter a new PIN:');
        pin = parseInt(newPin);
        alert('PIN changed successfully.');
        transactionHistory.push('Changed PIN');
    } else {
        alert('Incorrect current PIN. PIN change failed.');
    }
}

function showHistory() {
    if (transactionHistory.length === 0) {
        document.getElementById('atm-output').textContent = 'No transactions found.';
    } else {
        document.getElementById('atm-output').textContent = transactionHistory.join('\n');
    }
}
