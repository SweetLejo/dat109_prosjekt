"use strict";

window.onload = function() {
    // Initially activate the "Brukere" tab
    document.querySelector(".tablinks[onclick*='Min-side']").click();
};


function expandView(evt, tabName){
    // Variable for the current tab's content
    let tabcontent;

    // Get the element of the current tab content
    tabcontent = document.getElementById(tabName);

    // If the current tab is already displayed, hide it and remove "active" class
    if (tabcontent.style.display === "block") {
        tabcontent.style.display = "none";
        evt.currentTarget.classList.remove("active");
    } else {
        // Hide all other tabs
        hideAllTabs();

        // Show the current tab and add "active" class
        tabcontent.style.display = "block";
        evt.currentTarget.classList.add("active");
    }
}

function hideAllTabs(){
    let i, tabcontent, tablinks;

    // Get all elements with class="tabcontent" and hide them
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    // Get all elements with class="tablinks" and remove the class "active"
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].classList.remove("active");
    }

}

function searchTable(tabContentId, columnIndex) {
    let input, filter, table, tr, td, i, txtValue;
    input = document.querySelector(`#${tabContentId} .search-input`);
    filter = input.value.toUpperCase();
    table = document.getElementById(`${tabContentId}Table`);
    tr = table.getElementsByTagName("tr");

    // Loop through all table rows, and hide those who don't match the search query
    for (i = 1; i < tr.length; i++) { // Start from 1 to skip heading row
        td = tr[i].getElementsByTagName("td")[columnIndex];
        if (td) {
            txtValue = td.textContent || td.innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}
