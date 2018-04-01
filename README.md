# Rock Paper Scissor

cli rock-paper-scissor game in scala

## Project structure

Project is built with [mill](http://www.lihaoyi.com/post/MillBetterScalaBuilds.html), and
as such it adopts mill's project structure: the application is split into two modules

* core: contains the game itself
* cli: contains a cli interface for the game

[Install mill](http://www.lihaoyi.com/mill/index.html#installation)

### Import

To prepare the project for intellij, run `mill mill.scalalib.GenIdeaModule/idea`
in the project folder (YMMV)

## Test

`mill all core.test cli.test`

or, to keep testing as the code changes,

`mill --watch all core.test cli.test`

NOTE:

Need to find out how to integrate `scoverage` with `mill`

## Build

`mill cli.assembly`

## Run

`java -cp out/cli/assembly/dest/out.jar rockpaperscissors.cli.RockPaperScissorsCli`

(`mill cli.assembly && java -cp out/cli/assembly/dest/out.jar rockpaperscissors.cli.RockPaperScissorsCli`)

NOTE:

Both `mill cli.run` and `mill cli.runLocal` seem to steal the stdin, thus preventing the app
from reading user input. The former forks a new jvm so the behavior can be expected, the second one
doesn't: need to investigate it further.