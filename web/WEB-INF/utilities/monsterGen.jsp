<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="/DndBuddy/resources/styles/global.css" />
    </head>
</head>
<body>
    <jsp:include page="/WEB-INF/components/navbar.jsp"/>
    <div class="container">
        <div class="row character-title">
            <div class="col d-flex justify-content-between">
                <h1>Monster Generator</h1>
            </div>
        </div>
        <button class="my-5" onclick="getRandomMonster()">Generate monster</button>
        <br>
        <h3 class="my-5 text-center bg-light lg-shadow" id="monster-name">Click the button!</h3>
    </div>

<script>    
    function getRandomMonster() {
        getJSON('https://www.dnd5eapi.co/api/monsters').then(function(data) {
            let rand = Math.random()*data.count;
            rand = Math.ceil(rand);
            document.getElementById("monster-name").innerText = data.results[rand].name;
            }, function(status) {
                alert(status);
        });
    };
    
    var getJSON = function(url) {
      return new Promise(function(resolve, reject) {
        var xhr = new XMLHttpRequest();
        xhr.open('get', url, true);
        xhr.responseType = 'json';
        xhr.onload = function() {
          var status = xhr.status;
          if (status === 200) {
            resolve(xhr.response);
          } else {
            reject(status);
          }
        };
        xhr.send();
      });
    };
</script>

</body>
<style>
    .character-title{
        border-bottom: solid 3px black
    }
</style>
</html>
