US-06: Para modificar meus arquivos, enquanto usuário, eu
preciso editar os arquivos, salvando no servidor seu conteúdo,
para que as modificações possam ser visualizadas novamente.
Critérios de aceitação: O sistema deve apresentar uma forma de
salvar o arquivo durante a edição do seu conteúdo. Este conteúdo
será por enquanto armazenado em memória (se reiniciar o servidor,
perdem-se os conteúdos). As mudanças também podem ser
descartadas durante a edição.

US-07: Para modificar meus arquivos, enquanto usuário, eu
preciso renomear o arquivo ou pasta, incluindo nome ou
extensão.
Critérios de aceitação: Apenas as extensões md e txt serão aceitas
durante o processo de renomeação.

US-08: Para ter acesso a mais de um tipo de arquivo texto,
enquanto usuário, eu preciso armazenar e editar arquivos com
texto markdown (extensão).
Critérios de aceitação: Não é necessário verificar se o conteúdo do
arquivo está usando a sintaxe markdown.

US-09: Para melhor trabalhar em conjunto com outras pessoas,
enquanto usuário, eu preciso compartilhar arquivos texto.
Critérios de aceitação: Dois tipos de compartilhamento serão
permitidos: compartilhamento de leitura e compartilhamento de
escrita. Por enquanto o compartilhamento em si não precisa ser
persistente (funciona apenas na sessão atual dos usuários). Se um
usuário receber compartilhamento de leitura, ele não poderá editar
o arquivo.


US-10: Para poder colaborar com outras pessoas, enquanto
usuário, eu preciso receber uma notificação quando algum
arquivo for compartilhado comigo.
Critérios de aceitação: A notificação pode aparecer no sistema
quando o usuário logar na sua conta (não precisa aparecer em
tempo real).


US-11: Para acessar o sistema em funcionamento de forma
mais simples, enquanto usuário, preciso que o sistema esteja
executando em um servidor externo, para que eu possa usar
apenas um link para colocá-lo em funcionamento.
Critérios de aceitação: utilizar o Heroku ou outro hospedeiro
gratuito de sistemas web.
