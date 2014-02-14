#PaintEq

An attempt to make a user friendly front end to write equations. Potentially intelligence can be added, so that it also checks for validity of a user's approach (for example when proving a theorem) and even assists the user in symbolic computations. I intend to divide work on this project in phases, starting from 0. The first number in the version code represents the phase we're currently in. The next three numbers represent the date it was released on. It might be preferable to identify the version solely by these, since the different phases can be viewed as different projects.

Phase zero is developing an equation editor, in particular for an android device. There are definitely quite a few options out there, some even open source. I admit to being a little lazy towards researching on these options. I'd love to know if such systems are already developed (makes it a lot easier for me). This, however, does provide for a warm up. One feature I introduced was a symbols keyboard like the emoticon pads for many messaging apps. This will hopefully make typing equations on a mobile device a little convenient. This app currently just allows the user to type data as a LaTeX code that is rendered on demand on a WebView through Mathjax. By the end of this phase, one should be able to also have a stock of recently used symbols stored up for oneself, as well as allow one to store custom made symbols.

I'm a little clueless about the outline of the next phases, but here are some ideas:
->  Undo/redo functionality.
->  Ability to edit the rendered equation.
->  Ability to drag and drop symbols into the equations.
->  Interaction with a desktop/laptop and any related software present in it - for times when some heavylifting needs to be done.
->  Ability to parse the content the user has typed. For example it should be able to switch the sign of a term when it is sent to the other side of the equation.
->  Handwriting recognition!

Further design details coming soon. Suggestions are welcome.
