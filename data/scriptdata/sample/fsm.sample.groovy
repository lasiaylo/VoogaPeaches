import engine.fsm.FSM

fsm = new FSM([
        name1: [
                properties : [
                        property1: "bla1",
                        default: true
                ],
                transitions: [
                        name2: "{ -> return true }"
                ]
        ],
        name2: [
                properties : [
                        property1: "bla2",
                        default: false
                ],
                transitions: [
                        name1: "{ -> return false }"
                ]
        ]
])