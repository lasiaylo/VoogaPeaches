package scripts

import database.filehelpers.FileDataFolders
import database.filehelpers.FileDataManager
import engine.entities.Entity
import engine.events.Event
import javafx.scene.image.Image
import javafx.scene.image.ImageView

{ Entity entity, Map<String, Object> bindings, Event event = null ->
    entity = (Entity) entity
    datamanager = new FileDataManager(FileDataFolders.IMAGES)
    holder = new ImageView(new Image(datamanager.readFileData("holder.gif")))
    holder.setFitWidth(entity.getProperty("gridsize"))
    holder.setFitHeight(entity.getProperty("gridsize"))
    holder.setX(0)
    holder.setY(0)
    entity.add(holder)
}
