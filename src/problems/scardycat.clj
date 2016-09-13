(ns hanoi)

(defrecord State [tower-start tower-mid tower-finish]);;rename before Wednesday @ 2200!

(defn legal? [[tower1] [tower2]]
  (and (< (peek [tower1]) (peek[tower2]))))


(defn swap [tower-start tower-mid tower-finish]
  (let [old-value (pop [tower-start])
        new-value (cons [tower-mid])]
    ;;dont know what to put for stru yet.
    (assoc-in State [tower-start] (get-in [tower-start]))
    ;; over writing the current stru, and adding the old-value to the current tower, dont know if it works
    ;; getting late, see ya
    (assoc-in State [tower-mid] (cons old-value [tower-mid]))))


(defn children [state]
  (let [disk (peek (:tower-start state))]
    ;;code works to here
  (for [[T1 T2 T3] [tower-start tower-mid tower-finish]
        :let [new-T1 [tower-start]
              new-T2 [tower-mid]
              new-T3 [tower-finish]]
        :when (legal? new-T1 new-T2)
        :when (legal? new-T1 new-T3)
        :when (legal? new-T2 new-T3)
        :when (legal? new-T2 new-T1)
        :when (legal? new-T3 new-T1)
        :when (legal? new-T3 new-T2)]
    (-> State (swap (:tower state) [T1 T2 T3] [new-T1 new-T2 new-T3])
              [new-T1 new-T2 new-T3]))))

(peek [])
(peek (:tower-start State))
(let [disk (peek (:tower-start State))]
(println disk))
