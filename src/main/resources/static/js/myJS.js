// Alerte suppression
function confirmer_suppression()
{
    if(confirm('ATTENTION ! Vous allez supprimer un élément, voulez-vous poursuivre ?'))
    { return true; }
    else
    { return false; }
}

    function reste_a_saisir(nbCar,texte)
    {
        var restants=nbCar-texte.length;
        document.getElementById('caracteres').innerHTML=restants;
    }
