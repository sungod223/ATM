const board = document.getElementById('board');
const message = document.getElementById('message');
const resetButton = document.getElementById('reset');
const themeToggleButton = document.getElementById('theme-toggle');

let currentPlayer = 'X';
let gameState = Array(9).fill(null);
let gameActive = true;

const winningCombinations = [
    [0, 1, 2],
    [3, 4, 5],
    [6, 7, 8],
    [0, 3, 6],
    [1, 4, 7],
    [2, 5, 8],
    [0, 4, 8],
    [2, 4, 6],
];

function createBoard() {
    board.innerHTML = '';
    gameState.fill(null);
    gameActive = true;
    currentPlayer = 'X';
    message.textContent = `Player ${currentPlayer}'s turn`;

    for (let i = 0; i < 9; i++) {
        const cell = document.createElement('div');
        cell.classList.add('cell');
        cell.dataset.index = i;
        board.appendChild(cell);
    }
}

function checkWinner() {
    for (let combination of winningCombinations) {
        const [a, b, c] = combination;
        if (gameState[a] && gameState[a] === gameState[b] && gameState[a] === gameState[c]) {
            message.textContent = `Player ${currentPlayer} wins!`;
            gameActive = false;
            return;
        }
    }

    if (!gameState.includes(null)) {
        message.textContent = "It's a tie!";
        gameActive = false;
    }
}

function handleCellClick(event) {
    const cell = event.target;
    const index = cell.dataset.index;

    if (!gameActive || gameState[index]) return;

    gameState[index] = currentPlayer;
    cell.textContent = currentPlayer;

    checkWinner();

    if (gameActive) {
        currentPlayer = currentPlayer === 'X' ? 'O' : 'X';
        message.textContent = `Player ${currentPlayer}'s turn`;
    }
}

function toggleTheme() {
    document.body.classList.toggle('dark-mode');
    const isDarkMode = document.body.classList.contains('dark-mode');
    themeToggleButton.textContent = isDarkMode ? 'Switch to Light Mode' : 'Switch to Dark Mode';
}

resetButton.addEventListener('click', createBoard);
themeToggleButton.addEventListener('click', toggleTheme);

board.addEventListener('click', handleCellClick);

createBoard();
