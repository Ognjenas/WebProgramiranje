function encodeImageFileAsURL(element) {
    var file = element.files[0];
    var reader = new FileReader();
    reader.onloadend = function() {
        console.log('RESULTttt', reader.result)
    }
    reader.readAsDataURL(file);
    document.getElementById("picture").value =
    alert(document.getElementById("picture").value)
}

function encodeImageFileAsURL2() {

    var filesSelected = document.getElementById("fileFacility").files;

    if (filesSelected.length > 0) {
        if(document.getElementById("fileFacility").files[0].size > 1048576) {
            alert("Too big picture!!!")
        }
        var fileToLoad = filesSelected[0];

        var fileReader = new FileReader();

        fileReader.onload = function(fileLoadedEvent) {
             // <--- data: base64
            document.getElementById("pictureFacility").value = fileLoadedEvent.target.result;
        }
        fileReader.readAsDataURL(fileToLoad);
    }
}

function encodeImageFileAsURLOffer() {

    var filesSelected = document.getElementById("fileOffer").files;
    if (filesSelected.length > 0) {
        var fileToLoad = filesSelected[0];

        var fileReader = new FileReader();

        fileReader.onload = function(fileLoadedEvent) {
            // <--- data: base64
            document.getElementById("pictureOffer").value = fileLoadedEvent.target.result;
        }
        fileReader.readAsDataURL(fileToLoad);
    }
}

function encodeImageFileAsURLOfferEdit() {

    var filesSelected = document.getElementById("fileOfferEdit").files;
    if (filesSelected.length > 0) {
        var fileToLoad = filesSelected[0];

        var fileReader = new FileReader();

        fileReader.onload = function(fileLoadedEvent) {
            // <--- data: base64
            document.getElementById("pictureOfferEdit").value = fileLoadedEvent.target.result;
            document.getElementById("fileOfferEdit").src = fileLoadedEvent.target.result;
        }
        fileReader.readAsDataURL(fileToLoad);
    }
}
