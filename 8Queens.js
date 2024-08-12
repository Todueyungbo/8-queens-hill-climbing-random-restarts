function getRandomInt(max) {
    return Math.floor(Math.random() * max);
}

function generateBoard() {
    const board = [];
    for (let i = 0; i < 8; i++) {
        board[i] = getRandomInt(8);
    }
    return board;
}

function getConflicts(board) {
    let conflicts = 0;
    for (let i = 0; i < 8; i++) {
        for (let j = i + 1; j < 8; j++) {
            if (board[i] === board[j]) conflicts++; // Same row
            if (Math.abs(board[i] - board[j]) === j - i) conflicts++; // Same diagonal
        }
    }
    return conflicts;
}

function getBestNeighbor(board) {
    let bestBoard = board.slice();
    let minConflicts = getConflicts(board);
    
    for (let i = 0; i < 8; i++) {
        for (let j = 0; j < 8; j++) {
            if (board[i] === j) continue;
            const newBoard = board.slice();
            newBoard[i] = j;
            const newConflicts = getConflicts(newBoard);
            if (newConflicts < minConflicts) {
                minConflicts = newConflicts;
                bestBoard = newBoard;
            }
        }
    }
    
    return { board: bestBoard, conflicts: minConflicts };
}

function hillClimbing() {
    let board = generateBoard();
    let currentConflicts = getConflicts(board);
    while (true) {
        const { board: neighbor, conflicts: neighborConflicts } = getBestNeighbor(board);
        if (neighborConflicts >= currentConflicts) break;
        board = neighbor;
        currentConflicts = neighborConflicts;
    }
    return { board, conflicts: currentConflicts };
}

function hillClimbingWithRandomRestart(maxRestarts = 1000) {
    for (let i = 0; i < maxRestarts; i++) {
        const { board, conflicts } = hillClimbing();
        if (conflicts === 0) {
            return { board, restarts: i };
        }
    }
    return null; // No solution found within the max restarts
}

function printBoard(board) {
    console.log('Board configuration:', board);
    for (let i = 0; i < 8; i++) {
        let row = '';
        for (let j = 0; j < 8; j++) {
            row += (board[i] === j ? 'Q ' : '. ');
        }
        console.log(row);
    }
}

const result = hillClimbingWithRandomRestart();
if (result) {
    console.log(`Solution found after ${result.restarts} restarts:`);
    printBoard(result.board);
} else {
    console.log('No solution found.');
}