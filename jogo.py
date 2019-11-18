#GRUPO 
    #Gustavo Maciel
    #Leonardo Almeida
    #Paulo Rodrigues
    
from math import inf as infinity
from random import choice
import platform
import time
from os import system

HUMAN = -1
COMP = +1

board = [[0, 0, 0],
         [0, 0, 0],
         [0, 0, 0],]



def tabuleiro(state, pc, humano):

    chars = {-1: humano, +1: pc, 0: ' '}
    str_line = '---------------'


    print('\n' + str_line)
    for row in state:
        for cell in row:
            symbol = chars[cell]
            print(f'| {symbol} |', end='')
        print('\n' + str_line)



def vezPC(pc, humano):
    depth = len(checaVazio(board))
    if depth == 0 or FimDeJogo(board):
        return

    tabuleiro(board, pc, humano)

    if depth == 9:
        x = choice([0, 1, 2])
        y = choice([0, 1, 2])
    else:
        move = minimax(board, depth, COMP)
        x, y = move[0], move[1]

    set_move(x, y, COMP)
    time.sleep(1)


def vezHUMANO(pc, humano):
    depth = len(checaVazio(board))
    if depth == 0 or FimDeJogo(board):
        return

    move = -1
    moves = {
        1: [0, 0], 2: [0, 1], 3: [0, 2],
        4: [1, 0], 5: [1, 1], 6: [1, 2],
        7: [2, 0], 8: [2, 1], 9: [2, 2],
    }

    tabuleiro(board, pc, humano)

    while move < 1 or move > 9:
        try:
            move = int(input('Digite um valor de 1 a 9: '))
            coord = moves[move]
            can_move = set_move(coord[0], coord[1], HUMAN)

            if not can_move:
                print('Opção inválida!')
                move = -1
        except (EOFError, KeyboardInterrupt):
            print('...')
            exit()
        except (KeyError, ValueError):
            print('Opção inválida')


def evaluate(state):
    if vitoria(state, COMP):
        score = +1
    elif vitoria(state, HUMAN):
        score = -1
    else:
        score = 0
    return score

def vitoria(state, player):
    win_state = [
        [state[0][0], state[0][1], state[0][2]],
        [state[1][0], state[1][1], state[1][2]],
        [state[2][0], state[2][1], state[2][2]],
        [state[0][0], state[1][0], state[2][0]],
        [state[0][1], state[1][1], state[2][1]],
        [state[0][2], state[1][2], state[2][2]],
        [state[0][0], state[1][1], state[2][2]],
        [state[2][0], state[1][1], state[0][2]],
    ]
    if [player, player, player] in win_state:
        return True
    else:
        return False


def FimDeJogo(state):
    return vitoria(state, HUMAN) or vitoria(state, COMP)


def checaVazio(state):
    cells = []
    for x, row in enumerate(state):
        for y, cell in enumerate(row):
            if cell == 0:
                cells.append([x, y])
    return cells

def movimento_valido(x, y):
    if [x, y] in checaVazio(board):
        return True
    else:
        return False

def set_move(x, y, player):
    if movimento_valido(x, y):
        board[x][y] = player
        return True
    else:
        return False


# Implementação do algoritmo Minimax
def minimax(state, depth, player):
  
    if player == COMP:
        best = [-1, -1, -infinity]
    else:
        best = [-1, -1, +infinity]

    if depth == 0 or FimDeJogo(state):
        score = evaluate(state)
        return [-1, -1, score]

    for cell in checaVazio(state):
        x, y = cell[0], cell[1]
        state[x][y] = player
        score = minimax(state, depth - 1, -player)
        state[x][y] = 0
        score[0], score[1] = x, y

        if player == COMP:
            if score[2] > best[2]:
                best = score  # max value
        else:
            if score[2] < best[2]:
                best = score  # min value

    return best


def main():
    humano = ''
    pc = ''

    # Usuário quem escolhe a letra
    while humano != 'O' and humano != 'X':
        try:
            print('')
            humano = input('Escolha X ou O: ').upper()
        except (EOFError, KeyboardInterrupt):
            print('...')
            exit()
        except (KeyError, ValueError):
            print('Escolha da letra')

    # Escolha oposta ao do usuário
    if humano == 'X':
        pc = 'O'
    else:
        pc = 'X'

    # Jogo em loop
    while len(checaVazio(board)) > 0 and not FimDeJogo(board):
    
        vezHUMANO(pc, humano) 
        vezPC(pc, humano)
    
    if vitoria(board, HUMAN):
        tabuleiro(board, pc, humano)
        print('\nVitória humano')
    elif vitoria(board, COMP):
        tabuleiro(board, pc, humano)
        print('\nVitória pc')
    else:
        tabuleiro(board, pc, humano)
        print('\nEmpate! :o')
    exit()


if __name__ == '__main__':
    main()