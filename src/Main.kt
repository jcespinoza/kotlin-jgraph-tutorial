// If this imports do not work, then you didn't add the library correctly
import com.mxgraph.swing.mxGraphComponent
import com.mxgraph.util.mxConstants
import com.mxgraph.view.mxGraph

//Everything should come from JavaFX, otherwise you've done something wrong
import javafx.application.Application
import javafx.embed.swing.SwingNode
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.input.MouseEvent
import javafx.scene.layout.FlowPane
import javafx.scene.layout.GridPane
import javafx.stage.Stage


// This is what makes this file the starting point of the program
fun main(args: Array<String>) {
    // The only thing it does is to launch our JavaFX application defined below
    Application.launch(MainApplication::class.java)
}

class MainApplication : Application() {
    override fun start(stage: Stage) {
        //Create a simple Graph Object with two vertices and one edge
        val graph = mxGraph()
        val parent = graph.defaultParent

        // This is a call to an extension function defined below
        // In other languages this would have looked like this: graph.update( () -> doSomething() )
        // In JavaScript, it would have looked like this: graph.update( function(){ //code; //moreCode; } )
        graph.update {
            // Creates a vertex
            // Gives it a parent, position, dimensions and style
            val vertexOne = graph.insertVertex(parent, null, "q0", 20.0, 20.0, 50.0, 50.0, "shape=ellipse;fillColor=white")

            // Creates another vertex. But this one looks like an accepted state
            val vertexTwo = graph.insertVertex(parent, null, "q1", 840.0, 450.0, 50.0, 50.0, "shape=doubleEllipse;fillColor=white")

            // Creates an edge between the two vertices
            graph.insertEdge(parent, null, "1", vertexOne, vertexTwo)

            // Creates an edge from one vertex to itself to demonstrate loops
            graph.insertEdge(parent, null, "0", vertexTwo, vertexTwo, "rounded=true;arcSize=12")
        }

        // Creates the embeddable graph swing component
        val graphComponent = mxGraphComponent(graph)

        //Allows vertices to have edges from them to themselves
        graph.isAllowLoops = true

        //Prevents edges from pointing to nothing
        graph.isAllowDanglingEdges = false

        //Prevent edge labels from being dragged somewhere absurd
        graph.isEdgeLabelsMovable = false

        //Allows the vertices to receive edges when they're touched anywhere and not just the center
        mxConstants.DEFAULT_HOTSPOT = 1.0

        //Creates a TextField
        val aTextField = TextField()

        // Creates a button with some text
        val aButton = Button("Print Whatever is on the TextField")

        // Creates an event handler for the button
        aButton.onMouseClicked = EventHandler<MouseEvent> {
            println("You have clicked the button while the string was " + aTextField.text)
        }

        //Create the actual container for the GUI
        val sceneRoot = FlowPane(SwingNode().apply {
                //Sets the graph as the content of the swing node
                content = graphComponent
            },
            aTextField,
            aButton
        )

        //Adds the Scene to the Stage, a.k.a the actual Window
        stage.scene = Scene(sceneRoot, 900.0, 700.0)

        //Shows the window
        stage.show()
    }

    // This function is an extension to the mxGraph class
    // I have no idea about how on this thing works
    // UPDATE: now I now how it works. this "block" parameter is actually a callable function object
    private fun  mxGraph.update(block: () -> Any) {
        // Apparently the model variable is a child of mxGraph instance we are extending
        model.beginUpdate()
        try {
            // I guess this prevents other threads from updating the model
            // Or it makes the Earth orbit around the sun, I'm not sure
            // UPDATE: turns out it has nothing to do with Earth's orbit
            block()
            // Thanks to Alex for pointing out that the "block" object come as a parameter,
            // And since it's a function, it can be executed by appending parenthesis
        }
        finally {
            model.endUpdate()
        }
    }
}