import random

def random_board(n=8):
    """Generate a random board for n-queens problem."""
    return [random.randint(0, n-1) for _ in range(n)]

def count_attacks(board):
    """Count the number of attacks on the board."""
    attacks = 0
    n = len(board)
    for i in range(n):
        for j in range(i + 1, n):
            if board[i] == board[j] or abs(board[i] - board[j]) == abs(i - j):
                attacks += 1
    return attacks

def get_neighbors(board):
    """Generate all possible neighboring states."""
    neighbors = []
    n = len(board)
    for i in range(n):
        for j in range(n):
            if board[i] != j:
                neighbor = board[:]
                neighbor[i] = j
                neighbors.append(neighbor)
    return neighbors

def hill_climbing(board):
    """Perform hill climbing to solve the n-queens problem."""
    current_board = board
    current_attacks = count_attacks(current_board)

    while True:
        neighbors = get_neighbors(current_board)
        next_board = min(neighbors, key=count_attacks)
        next_attacks = count_attacks(next_board)

        if next_attacks >= current_attacks:
            return current_board

        current_board = next_board
        current_attacks = next_attacks

def hill_climbing_with_random_restarts(n=8, max_restarts=1000):
    """Solve n-queens problem with hill climbing and random restarts."""
    for _ in range(max_restarts):
        board = random_board(n)
        solution = hill_climbing(board)
        if count_attacks(solution) == 0:
            return solution
    return None

# Solve the 8-queens problem
solution = hill_climbing_with_random_restarts()
if solution:
    print("Solution found:", solution)
else:
    print("No solution found after max restarts.")