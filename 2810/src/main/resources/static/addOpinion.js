console.log("ciao")
document.getElementById("addOpinionButton").addEventListener("click", function() {
    // Mostra il form per aggiungere un nuovo libro impostando "display: block"
    document.getElementById("opinionForm").style.display = "block";
    // nascondi il form per rimuovere il libro
    document.getElementById("removeOpinionForm").style.display = "none";
});

document.getElementById("newOpinionForm").addEventListener("submit", function(event) {
    // Impedisce il comportamento di submit predefinito (invio dei dati)
    event.preventDefault();

    // Recupera i valori inseriti dall'utente nei campi del form
    const title = document.getElementById("title").value;
    const year = document.getElementById("year").value;
    const publisher = document.getElementById("publisher").value;

    // Riferimento alla tabella dove verranno aggiunti i nuovi libri
    const tableBody = document.getElementById("opinionTable").getElementsByTagName('tbody')[0];


    addNewRowV1(tableBody , title , year , publisher);

//    addNewRowV2(tableBody , title , year , publisher);



    // Nasconde il form di aggiunta libro dopo l'inserimento
    document.getElementById("opinionForm").style.display = "none";
    // Reset dei campi del form per future aggiunte
    document.getElementById("newOpinionForm").reset();



});

function addNewRowV1(tableBody , title, year,publisher){

    // Crea una stringa HTML per la nuova riga
    const newRowHTML = `
        <tr>
            <td>${title}</td> 
            
            <td><span class="badge bg-primary">${year}</span></td> 
            
            <td>${publisher}</td>
            
            <td><button class="btn btn-danger btn-sm deleteButton" ><i class="fa-solid fa-trash"> </i></button></td>
        </tr>
    `;
    // ${nome} serve per usare il valore interno alla variabile

    // Aggiunge la nuova riga alla tabella usando innerHTML
    tableBody.innerHTML += newRowHTML;
    updateBtn();
    console.log('Adding new Row , with method 1');
}
