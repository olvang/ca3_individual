
[![Build Status](https://travis-ci.com/olvang/ca3_individual.svg?branch=main)](https://travis-ci.com/olvang/ca3_individual)

Har tilføjet et simpelt Todo system, hvor en 'user' kan se alle todos og en 'admin' både kan se og tilføje nye todos. Både frontend og backend er lavet så det kun er admin der har adgang til at tilføje nye todos.
Har også ændret lidt i ombygningen af frontend.

Brugere:
<p>user:test123</p>
<p>admin:test123</p>

<p>Backend github: https://github.com/olvang/ca3_individual</p>
<p>Frontend github: https://github.com/olvang/ca3_frontend_individual</p>
<p>Backend deployed: https://vangomango.dk/CA3/</p>
<p>Frontend deployed: https://vangca3.surge.sh/</p>
<p>Travis: https://travis-ci.com/olvang/ca3_individual</p>

_____________________
<p>All of this expects you have a local user for your MySql databse called dev with ax2 as password</p>

<p>Remember to start your local docker envoriment and run "docker-compose up -d" where your docker image is installed </p>

<p>Remember to give travis acces to your repository through <br></p>
https://travis-ci.com/account/repositories --> Mange repositories on github

<p>Remember to set the envorimental varibles in the branch in travis.</p>
<p>REMOTE_USER = [Your server user]</p>
<p>REMOTE_PW = [Your server password]</p>

<p>Change or add your own connection string in EMF-Creator at line 43 and 46</p>
<p>Add the connection string in docker-compose.yml on your digitalOcean under "enviroment"</p>

<p>Create database on digitalOcean to match your local setup</p>

<p>Rename the value in the <remote.server> tag to [Your server ip]/manager/text on line 19 </p>

<p>Change the value on line 24 in persistence.xml to match the local database on your machine</p>
