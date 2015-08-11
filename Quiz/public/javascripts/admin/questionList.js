$(document).ready(function(){
	
	// Hide detailed export buttons
	$(".export").hide();
	
	// Predefined method for table sorting
	$('#question-table').DataTable( {
		// "searching": false,
		"language": {
			"url": "//cdn.datatables.net/plug-ins/1.10.7/i18n/Croatian.json"
		}
	});
	
	// DataTable
    var table = $('#question-table').DataTable();
	
	// Method call for filtering table content on keystroke
	$("#filter").on('change keyup paste mouseup', function() {
		filter(table);
	});
	
	$('#filterType').change(function(e){
		filter(table);
	});
	
	$(".export-questions").on("click", function() {
		$(".export").toggle();
	});
	
	// Starts dialog button on delete
	$(".delete-button").click(function(ev) {
	    var link = this;

	    ev.preventDefault();
	    
	    var href = link.href;
	    var hrefArray = href.split("/");
	    var hrefLen = hrefArray.length;
	    
	    var questionId = hrefArray[hrefLen-1];
	    var trName = "question-"+questionId;
	    var questionText = $("#"+trName + " > .table-questionText").html();
	    
	    var deleteQuestion = Messages("question.delete");
	     
	    $("<div>Jeste li sigurni da želite obrisati pitanje? <br><br> Id: "+questionId+"<br> Tekst: "+questionText+"</div>").dialog({
	        buttons: [
				{
					text: Messages('question.delete'),
					click: function() {
		                jsRoutes.controllers.QuestionController.deleteQuestion(questionId).ajax({
		                	success: function(data) {
		                		location.reload();
		                	}
		                });
					}
				},
				{
					text: Messages('question.cancel'),
					click: function() {
						$(this).dialog("close");
					}
				},
			]
	        
	    });
	});
});
/**
 * Function that invokes DataTables search method with filter parameter
 * and re-draws the whole question table with filtered results.
 */
function filter(table) {
	
	table.columns().search("").draw();		// resets the search; 
	
	var filter = $("#filter").val();
	var searchColumn = getColumnNumber($("#filterType").val());
	
	table.column(searchColumn).search(filter).draw();
	
}

/**
 * Function that returns predefined column number in question table based on 
 * <i>filterType</i> string which defines which search filter is used.
 * 
 * @param filterType string that defines which search filter is used
 * @returns {Number} predefined number for one of the columns in question table
 */
function getColumnNumber(filterType) {
	switch(filterType) {
		case "TEXT_FILTER":
			return 0;
		case "TYPE_FILTER":
			return 1;
		case "SUBJECT_FILTER":
			return 2;
		case "CHAPTER_FILTER":
			return 3
		case "DIFFICULTY_FILTER":
			return 4;
		case "SUBMITTER_FILTER":
			return 5;
		default:
			return 1;
	}
}


