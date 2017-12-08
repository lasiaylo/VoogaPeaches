import engine.entities.Entity
import engine.fsm.FSM
entity = new Entity()

fsm = new FSM(entity as Entity, [
        name1: [
                properties : [
                        property1: "bla1",
                        default  : true
                ],
                transitions: [
                        name2: "{entity -> return true }"
                ]
        ],
        name2: [
                properties : [
                        property1: "bla2"
                ],
                transitions: [
                        name1: "{ entity -> return false }"
                ]
        ]
])