<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="/DndBuddy/resources/styles/global.css" />
        <script src="https://kit.fontawesome.com/815f235d4b.js" crossorigin="anonymous"></script>
    </head>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="#">D&D Buddy</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="/DndBuddy/characters">Characters</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/DndBuddy/campaigns">Campaigns</a>
            </li>
            <li class="nav-item dropdown dropdown-hover">
                <a class="nav-link dropdown-toggle">Utilities</a>
                <div class="dropdown-menu">
                    <a class="dropdown-item" href="/DndBuddy/utilities/monster">Monster Generator</a>
                    <a class="dropdown-item" href="/DndBuddy/utilities/notes">My Notes</a>
                </div>
            </li>
        </ul>

        <form class="form-inline ml-auto" action="/DndBuddy/login" method="POST">
            <button class="btn btn-outline-secondary" name="method" value="logout"><i class="fas fa-sign-out-alt"></i></button>
        </form>
    </nav>
</body>
</html>


