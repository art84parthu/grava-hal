<!DOCTYPE html
            PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
            "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    <html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
        <link rel="stylesheet" type="text/css" href="/css/style.css">
    </head>
    <body>

<#if game??>
    <div id="box">
    <table class="buttons width-20">
    <tr>
        <td colpan="1"></td>
        <td colspan="8" style="text:align:center">
            <#if error?? >
                ${error}
            <#elseif won_by??>
                <label> Game over! Won by Player ${won_by}</label>
            <#else>
             <#if which_player == game.getPlayer1_id()>
                <label id="pl1">Player-One's turn. Click on any non empty pit to play! </label>
             <#else>
                <label id="pl2">Player-Two's turn. Click on any non empty pit to play! </label>
             </#if>
            </#if>
        </td>
    </tr>
    <tr>
    <#if player1_pits??>
        <td > One => </td>
        <td rowspan="2"> <label> ${player1_pits[6]} </label> </td>
        <#if which_player == game.getPlayer1_id() && game.getWon_by() < 0 >
        <td> <a href="/play/5/${game.getPlayer1_id()}" >${player1_pits[5]}</a></td>
        <td> <a href="/play/4/${game.getPlayer1_id()}" >${player1_pits[4]}</a></td>
        <td> <a href="/play/3/${game.getPlayer1_id()}" >${player1_pits[3]}</a></td>
        <td> <a href="/play/2/${game.getPlayer1_id()}" >${player1_pits[2]}</a></td>
        <td> <a href="/play/1/${game.getPlayer1_id()}" >${player1_pits[1]}</a></td>
        <td> <a href="/play/0/${game.getPlayer1_id()}" >${player1_pits[0]}</a></td>
        <#else>
        <td> <label> ${player1_pits[5]} </label> </td>
        <td> <label> ${player1_pits[4]} </label> </td>
        <td> <label> ${player1_pits[3]} </label> </td>
        <td> <label> ${player1_pits[2]} </label> </td>
        <td> <label> ${player1_pits[1]} </label> </td>
        <td> <label> ${player1_pits[0]} </label> </td>
        </#if>
        <td rowspan="2"> <label> ${player2_pits[6]} </label> </td>
    </#if>
    </tr>
    <tr>
    <#if player2_pits??>
        <td>Two =></td>
        <#if which_player == game.getPlayer2_id() && game.getWon_by() < 0 >
        <td> <a href="/play/0/${game.getPlayer2_id()}" >${player2_pits[0]}</a></td>
        <td> <a href="/play/1/${game.getPlayer2_id()}" >${player2_pits[1]}</a></td>
        <td> <a href="/play/2/${game.getPlayer2_id()}" >${player2_pits[2]}</a></td>
        <td> <a href="/play/3/${game.getPlayer2_id()}" >${player2_pits[3]}</a></td>
        <td> <a href="/play/4/${game.getPlayer2_id()}" >${player2_pits[4]}</a></td>
        <td> <a href="/play/5/${game.getPlayer2_id()}" >${player2_pits[5]}</a></td>
        <#else>
        <td> <label> ${player2_pits[0]} </label> </td>
        <td> <label> ${player2_pits[1]} </label> </td>
        <td> <label> ${player2_pits[2]} </label> </td>
        <td> <label> ${player2_pits[3]} </label> </td>
        <td> <label> ${player2_pits[4]} </label> </td>
        <td> <label> ${player2_pits[5]} </label> </td>
        </#if>
    </#if>
    </tr>
    </table>
    </div>
</#if>

</body>
</html>
