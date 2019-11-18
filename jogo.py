import time

class Jogo:
	def __init__(self):
		self.comecar_jogo()

	def comecar_jogo(self):
		self.current_state = [['.', '.', '.'],
								['.', '.', '.'],
								['.', '.', '.']]
		self.player_turn = 'X'

	def tabuleiro(self):
		for i in range(0, 3):
			for j in range(0, 3):
				print('{}|'.format(self.current_state[i][j]), end=" ")
			print()
		print()

	def movimentoValido(self, px, py):
		if px < 0 or px > 2 or py < 0 or py > 2:
			return False
		elif self.current_state[px][py] != '.':
			return False
		else:
			return True

	def resultado(self):
		for i in range(0, 3):
			if(self.current_state[0][i] != '.' and
				self.current_state[0][i] == self.current_state[1][i] and
				self.current_state[1][i] == self.current_state[2][i]):
				return self.current_state[0][i]

		for i in range(0, 3):
			if(self.current_state[i] == ['X', 'X', 'X']):
				return 'X'
			elif(self.current_state[i] == ['O', 'O', 'O']):
				return 'O'

		if (self.current_state[0][0] != '.' and
        	self.current_state[0][0] == self.current_state[1][1] and
        	self.current_state[0][0] == self.current_state[2][2]):
			return self.current_state[0][0]

		if(self.current_state[0][2] != '.' and
			self.current_state[0][2] == self.current_state[1][1] and
			self.current_state[0][2] == self.current_state[2][0]):
			return self.current_state[0][2]



		for i in range(0, 3):
			for j in range(0, 3):
				if(self.current_state[i][j] == '.'):
					return None

		return '.'



	def max(self):
		maxv = -2

		px = None
		py = None

		result = self.resultado()

		if result == 'X':
			return(-1, 0, 0)
		elif result == 'O':
			return(1, 0, 0)
		elif result == '.':
			return(0, 0, 0)

		for i in range(0, 3):
			for j in range(0, 3):
				if self.current_state[i][j] == '.':

					self.current_state[i][j] = 'O'
					(m, min_i, min_j) = self.min()

					if m > maxv:
						maxv = m
						px = i
						py = j

					self.current_state[i][j] = '.'
		return(maxv, px, py)


	def min(self):
		minv = 2
		qx = None
		qy = None

		result = self.resultado()

		if result == 'X':
			return (-1, 0, 0)
		elif result == 'O':
			return (1, 0, 0)
		elif result == '.':
			return (0, 0, 0)

		for i in range(0, 3):
			for j in range(0, 3):
				if self.current_state[i][j] == '.':
					self.current_state[i][j] = 'X'
					(m, max_i, max_j) = self.max()
					if m < minv:
						qx = i
						qy = j

					self.current_state[i][j] = '.'

		return (minv, qx, qy) 


	def jogar(self):
		while True:
			self.tabuleiro()
			self.result = self.resultado()


			if self.result != None:
				if self.result == 'X':
					print("O vencedor foi X!")
				elif self.result == 'O':
					print("O vencedor foi O!")
				elif self.result == '.':
					print("Empate")

				self.comecar_jogo()
				return


			if self.player_turn == 'X':

				while True: 
					px = int(input('Digite o valor da linha (0 a 2): '))
					py = int(input('Digite o valor da linha (0 a 2): '))

					(qx, qy) = (px, py)

					if self.movimentoValido(px, py):
						self.current_state[px][py] = 'X'
						self.player_turn = 'O'
						break
					else:
						print("Movimento inávalido!")
			else:
				(m, px, py) = self.max()
				self.current_state[px][py] = 'O'
				self.player_turn = 'X'



def main():
	j = Jogo()
	j.jogar()

if __name__ == "__main__":
	main()






