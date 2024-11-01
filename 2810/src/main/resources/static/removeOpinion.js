console.log("ciao");
document.getElementById("removeOpinionButton").addEventListener("click", function() {
    document.getElementById("removeOpinionForm").style.display = "block";
    document.getElementById("opinionForm").style.display = "none";

});
updateBtn();
function removeOpinionByNumber(event) {
    event.preventDefault(); 

    
    const opinionNumber = parseInt(document.getElementById("opinionNumber").value);
    console.log(opinionNumber);
    
    const rows =document.getElementById('opinionTable').querySelectorAll('tr');
    console.log(rows);
    
    if (opinionNumber > 0 && opinionNumber <= rows.length) {
        rows[opinionNumber ].remove(); 
        document.getElementById("removeOpinionForm").style.display = "none"; 
            document.getElementById("removeOpinionByNumberForm" +"ByNumberForm").reset(); 
    } else {
        alert("Numero opinione non valido!"); 
    }
}


function updateBtn() {
    document.querySelectorAll(".deleteButton").forEach(button => {
        button.addEventListener("click", function() {
            const row = button.closest("tr");
            row.parentNode.removeChild(row);
        });
    });

}



