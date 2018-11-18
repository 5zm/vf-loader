(function() {
  var viewReload = function() {
    $.ajax({
      type: 'GET',
      url: '/files',
      dataType: 'json'
    })
    .then(
      function(json) {
        console.log('success');
        // $(target).html("");
        $(target).children().remove();
        var count = json.length;
        for (i = 0; i < count; i++) {
          var item = json[i];
          if (item.fileInfoList.length > 0) {
            item['isNotEmpty'] = true;
          } else {
            item['isNotEmpty'] = false;
          }
          // render file list table
          render(item, '#target');
        }
        // add listener to new delete button
        addDeleteButtonListener();
        // reflesh file type select box
        refleshFileTypeSelectbox(json);
      },
      function() {
        console.log('error');
      }
    );
  }
  
  var refleshFileTypeSelectbox = function(json) {
    var selectBox = $('#uploadFileType');
    selectBox.children().remove();
    selectBox.append($('<option>').attr({value: ''}).text('Unselected'));
    var count = json.length;
    for (i = 0; i < count; i++) {
      var item = json[i];
      selectBox.append($('<option>')
        .attr({value: item.storeConfig.fileType})
        .text(item.storeConfig.fileTypeName));
    }
  };
  
  var addDeleteButtonListener = function() {
    var nodeList = document.getElementsByName("deleteButton");
    var count = nodeList.length;
    for(i = 0; i < count; i++) {
      var button = nodeList[i];
      button.addEventListener (
        'click',
        function (e) {
          console.log(e.target.id);
          $.ajax({
            type: 'DELETE',
            url: e.target.id
          })
          .then(
            function(response) {
              console.log("delete success");
              console.log(response);
              viewReload();
            },
            function(response) {
              console.log("delete error");
              console.log(response);
            }
          );
          
        }, false);
    }
  };

  var render = function(data, target) {
    var template=$("#template").html();
    var html = Mustache.render(template, data);
    $(target).append(html);
  };

  var post_file = function(upload_file, selectedFileType) {
    var message = document.getElementById("message");
    message.innerHTML = "now uploading!";

    var content_length = upload_file.size
    var content_type = upload_file.type
    var file_name = upload_file.name;

    var xhr = new XMLHttpRequest();
    var url = '/upload/' + selectedFileType;
    xhr.open('POST', url, true);
    xhr.setRequestHeader('Content-type', content_type);
    // xhr.setRequestHeader('Content-Length', content_length);
    xhr.setRequestHeader('X-FILE-NAME', encodeURIComponent(file_name));

    xhr.onload = function(e) {
      if (xhr.readyState == 4) {
        if( xhr.status == 200 || xhr.status == 201 ) {
          message.innerHTML = "Complete upload file.";
          var responseData = xhr.responseText;
          console.log(responseData);
          viewReload();
          console.log("viewReload()");
        } else {
          message.innerHTML = "Error in uploading.";
          var responseData = xhr.responseText;
          console.log(responseData);
        }
      }
    };

    var progress = document.getElementById("uploadProgress");
    xhr.upload.onprogress = function(e) {
      if (e.lengthComputable) {
        progress.value = (e.loaded / e.total) * 100;
      }
    };

    xhr.send(upload_file);
  };



  $(window).on("load", function() {
    viewReload();
    
    document.getElementById("refleshButton").addEventListener (
      'click',
      function(e) {
        console.log("refleshButton : execute");
        viewReload();
      }, false);
      
    document.getElementById("uploadClearButton").addEventListener (
      'click',
      function(e) {
        var message = document.getElementById("message");
        message.innerHTML = "Please select upload file and file type.";
        var progress = document.getElementById("uploadProgress");
        progress.value = 0;
      },false);

    document.getElementById("uploadButton").addEventListener (
      'click',
      function() {
        var element_file = document.getElementById("uploadFile");
        var upload_file = element_file.files[0];
        var selectedFileType = document.getElementById("uploadFileType").value;
        
        var message = document.getElementById("message");
        if ("" == selectedFileType) {
          message.innerHTML = "[ERROR] Please select file type.";
          return false;
        }
        if (typeof upload_file === 'undefined'){
          message.innerHTML = "[ERROR] Please select upload file.";
          return false;
        }
        console.log(upload_file);
        
        post_file(upload_file, selectedFileType);
      }, false);

    
  });

})();
