# script-lol-messenger
Script de enviar mensagens de acordo com histórico no League of Legends

## Ideia
Fazer um script que, sempre que algum amigo seu perder/ganhar uma partida no lol, enviar uma mensagem automatizada para um grupo/chat do discord.
Utilizei o @Scheduled do Spring Boot pra checar, a cada minuto, o histórico dos que eu escolhi. Fazendo chamadas para a API da Riot, é possível
recuperar essa e várias outras informações. Por fim, utilizando a API do discord, envio uma mensagem para um grupo definido, caso o jogador tenha 
ganhado/perdido uma partida nos últimos 60 segundos.

### Tecnologias 
Java 17 com Spring Boot 3.1.5, API's do Discord e da Riot
