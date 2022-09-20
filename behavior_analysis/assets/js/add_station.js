 $(function() {
    $( "#dialog" ).dialog({
      autoOpen: false,
    });
 
    $( "#ok" ).click(function() {
      $( "#dialog" ).dialog( "open" );
    });
  });