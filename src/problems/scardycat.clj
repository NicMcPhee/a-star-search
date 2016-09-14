(ns hanoi)

(defrecord State [tower-start tower-mid tower-finish]);;rename before Wednesday @ 2200!

(defn legal? [tower1 tower2]
  (and (< tower1 tower2)))


(defn swap [tower-start tower-mid tower-finish]
  (let [old-value (pop [tower-start])
        new-value (cons [tower-mid])]
    (assoc-in State [tower-start] (get-in [tower-start]))
    ;; over writing the current stru, and adding the old-value to the current tower,
    ;;dont know if it works
    (assoc-in State [tower-mid] (cons old-value [tower-mid]))))


(defn children [state]
  ;;tower 1
  (let [disk (peek (:tower-start state))]
    ;;code works to here
  (for [disk [[one two] [one three]]
        :let [new-T1 [tower-start]
              new-T2 [tower-mid]]]
        :when (legal? (peek (:State tower-start)) (peek (:State tower-mid)))
        :when (legal? (peek (:State tower-start)) (peek (:State tower-finish)))
    ;;-> stands for pushing something into the vector. ie
    ;; (->State [] [] []) will puch into the 3 vectors that were created.
    (->State (swap (:tower state) [T1 T2 T3] [new-T1 new-T2 new-T3])
              [new-T1 new-T2 new-T3]))
;;   ;;tower 2
;;   (let [disk (peek (:tower-mid state))]
;;     ;;code works to here
;;   (for [disk [[two one] [two three]]
;;         :let [new-T1 [tower-start]
;;               new-T2 [tower-mid]]
;;         :when (legal? new-T1 new-T2)
;;         :when (legal? new-T1 new-T3)]
;;     (-> State (swap (:tower state) [T1 T2 T3] [new-T1 new-T2 new-T3])
;;               [new-T1 new-T2 new-T3])))
;;   ;;tower 3
;;   (let [disk (peek (:tower-finish state))]
;;     ;;code works to here
;;   (for [disk [[three one] [three two]]
;;         :let [new-T1 [tower-start]
;;               new-T2 [tower-mid]]
;;         :when (legal? new-T1 new-T2)
;;         :when (legal? new-T1 new-T3)]
;;     (-> State (swap (:tower state) [T1 T2 T3] [new-T1 new-T2 new-T3])
;;               [new-T1 new-T2 new-T3])))
))




(peek [])
(peek (:tower-start State))
(let [disk (peek (:tower-start State))]
(println disk))
(legal? 1 2)

(defrecord boardtest [T1 T2 T3])
(def foo (->boardtest [1] [0] [0]))
(println foo)

