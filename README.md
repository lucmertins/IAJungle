# IAJungle

Projeto elaborado como trabalho final para a disciplina de Fundamentos de Inteligência Artificial do PPGC da UFPel 2016/01

O jogo que foi utilizado para aprovação na disciplina foi desenvolvido em Java, com 3 módulos, sendo 1 o servidor de rede, 
1 o módulo visual para o usuário e um módulo sendo a IA jogando.

O servidor vai a cada 2 conexões consecutivas estabelecendos os adversários, que podem ser dois humanos, 1 humano x computador 
ou 2 processos computador.

Código esta em evolução, buscando na parte java melhorar o rendimento e para permitir que a parte IA seja programada em C, 
visando averiguar se a linguagem de programação afeta o rendimento do jogo.


JOGO: Simple Jungle


TABULEIRO:
7x7

 
REGRAS:

Elefante mata tigre e cachorro

Tigre mata cachorro e rato

Cachorro mata o rato

Rato mata o elefante

Igual mata Igual

 

VITÓRIA:

Ganha o animal que chegar na toca do adversário

Ganha quem matar todos os inimigos

 

CASO DE EMPATE:
?Somatório das peças que foram perdidas no jogo de ida e volta? (falta decidir)
?Melhor de 3 com adversários já eliminados? (falta decidir)

 

CENÁRIO:

Toca está no meio e na lateral em lados opostos

 

_ R _ X _ E _

_ _ T _ C _ _

= = = = = = =

= = = = = = =

= = = = = = =

_ _ C _ T _ _

_ E _ X _ R _


R-Rato

X - Toca

E - Elefante

T - Tigre

C - Cachorro

 

MOVIMENTO:

Animais movem-se uma casa e não movem-se na diagonal.