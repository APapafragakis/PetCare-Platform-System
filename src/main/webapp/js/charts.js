// charts.js

function PetStats() {
    $.ajax({
        url: 'PetStatistics',
        method: 'GET',
        dataType: 'json',
        success: function(data) {
            drawPetChart(data);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error('Error fetching pet statistics:', textStatus, errorThrown);
        }
    });
}

function drawPetChart(data) {
    google.charts.load('current', { 'packages': ['corechart'] });
    google.charts.setOnLoadCallback(function () {
        var dataTable = new google.visualization.DataTable();
        dataTable.addColumn('string', 'Pet Type');
        dataTable.addColumn('number', 'Count');

        dataTable.addRow(['Cats', data.catCount]);
        dataTable.addRow(['Dogs', data.dogCount]);

        var options = {
            title: 'Number of Cats and Dogs',
            pieHole: 0.4,
        };

        var chart = new google.visualization.PieChart(document.getElementById('chartContainer'));
        chart.draw(dataTable, options);
    });
}

function ClientStats() {
    $.ajax({
        url: 'ClientsStats', 
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            drawPetOwnersAndKeepersChart(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error('Error fetching pet owners and keepers statistics:', textStatus, errorThrown);
        }
    });
}

function drawPetOwnersAndKeepersChart(data) {
    google.charts.load('current', { 'packages': ['corechart'] });
    google.charts.setOnLoadCallback(function () {
        var dataTable = new google.visualization.DataTable();
        dataTable.addColumn('string', 'Role');
        dataTable.addColumn('number', 'Count');

        dataTable.addRow(['Pet Keepers', data.petKeepers]);
        dataTable.addRow(['Pet Owners', data.petOwners]);

        var options = {
            title: 'Number of Pet Keepers and Pet Owners',
            pieHole: 0.4,
        };

        var chart = new google.visualization.PieChart(document.getElementById('chartContainerOwnersAndKeepers'));
        chart.draw(dataTable, options);
    });
}
