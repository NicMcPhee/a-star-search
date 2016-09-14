(ns hanoi)

(defrecord State [tower-start tower-mid tower-finish]);;rename before Wednesday @ 2200!

(defn legal? [tower1 tower2]
;;   (and (< tower1 tower2)))
  (and (< (peek :tower1) (peek :tower2))))


;; (defn swap orig-tower [tower-start tower-mid tower-finish]
;; ;;   (let [old-value (pop [tower-start])
;; ;;         new-value (cons [tower-mid])]
;;   (let [old-value (peek [:orig-tower])]
;;     (pop :orig-tower)

;;                                                             ;;Maybe, add a :when case to check against itself?
;;     (assoc-in State (let [disk [peek (:tower-start State)]]
;;   (println disk))[tower-start] (get-in [tower-start]))
;;     ;; over writing the current stru, and adding the old-value to the current tower,
;;     ;;dont know if it works
;;     (assoc-in State [tower-mid] (cons old-value [tower-mid]))))

;;the orig tower should have been popped by now.


;; Working on Swap, Need to interate from disk over all the towers.
(defn swap [disk tower1 tower2 tower3]
  [(conj tower1 disk) tower2 tower3])

(swap 1 [2] [3] [4])

(for [x [1]
      y [[2] [3] [4]]]
  (conj y x))


;; (swap1 3 50)

;; (defn children [state]
;;   (let [disk (peek [:tower-start State])]


;; (defn children [state]
;;   (let [disk (peek [:tower-start State])]
;;       :when (legal? [:tower-start State] [:tower-mid State])
;;       (->State (swap [:tower-start State] State)));;move the ->State to the swap??
;; ;;     (for [:tower-finish]
;; ;;       :let(defn swap [disk tower1 tower2 tower3]
;;   (conj disk tower1))
;; (swap 6 [1 2] [4] [3])
;; ;;       :when)
;; ;;     )
;; ;;   (let [:tower-mid]
;; ;;     (for [:tower-start]
;; ;;       )
;; ;;     (for [:tower-finish]
;; ;;       )
;; ;;     )
;; ;;   (let [:tower-finish]
;; ;;     (for [:tower-start]
;; ;;       )
;; ;;     (for [:tower-mid]
;; ;;       )
;; ;;     )
;;   )



;;   ;;tower 1
;;   (let [disk (peek (:tower-start state))]
;;     ;;code works to here
;;   (for [disk [[one two] [one three]]
;;         :let [new-T1 [tower-start]
;;               new-T2 [tower-mid]]]
;;         :when(let [disk [peek (:tower-start State)]]
;;         :when (legal? (peek (:State tower-start)) (peek (:State tower-finish)))
;;     ;;-> stands for pushing something into the vector. ie
;;     ;; (->State [] [] []) will puch into the 3 vectors that were created.
;;     (->State (swap (:tower state) [T1 T2 T3] [new-T1 new-T2 new-T3])
;;               [new-T1 new-T2 new-T3]))
;; ;;   ;;tower 2
;; ;;   (let [disk (peek (:tower-mid state))]
;; ;;     ;;code works to here
;; ;;   (for [disk [[two one] [two three]]
;; ;;         :let [new-T1 [tower-start]
;; ;;               new-T2 [tower-mid]]
;; ;;         :when (legal? new-T1 new-T2)
;; ;;         :when (legal? new-T1 new-T3)]
;; ;;     (-> State (swap (:tower state) [T1 T2 T3] [new-T1 new-T2 new-T3])
;; ;;               [new-T1 new-T2 new-T3])))
;; ;;   ;;tower 3
;; ;;   (let [disk (peek (:tower-finish state))]
;; ;;     ;;code works to here
;; ;;   (for [disk [[three one] [three two]]
;; ;;         :let [new-T1 [tower-start]
;; ;;               new-T2 [tower-mid]]
;; ;;         :when (legal? new-T1 new-T2)
;; ;;         :when (legal? new-T1 new-T3)]
;; ;;     (-> State (swap (:tower state) [T1 T2 T3] [new-T1 new-T2 new-T3])
;; ;;               [new-T1 new-T2 new-T3])))
;; ))




;; (peek [])
;; (peek (:tower-start State))
;; (let [disk (peek (:tower-start State))]
;; (println disk))
;; (legal? 1 2)


;; (def foo (->State  [1 2 3] [4] [5]))
;; (println foo)
;; (println (pop (:tower-start foo)))

;; (let [disk (peek (:tower-start State))]
;;   (println disk))

;; (println (- 18 17))
;; (println (+ 5 (- 3)))
;; (println "Go!")
;; (defrecord boardtest [T1 T2 T3])
;; (def foo (->boardtest [1] [0] [0]))
;; (println foo)
;; (conj (pop (:T1 boardtest)) (:T3 boardtest))
;; (println foo)
