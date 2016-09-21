(ns problems.towers)

(defrecord State [vec-of-pegs])

(def start-state (->State [[4 3 2 1] [] []]))
(def goal-state (->State [[] [4 3 2 1] []]))


(defn legal? [new-state]
  (let [stack-order (and (map #(if (< (count %) 2)
                                        true
                                        (apply > %)) (:vec-of-pegs new-state)))

        all-disks (apply concat (:vec-of-pegs new-state))
        correct-disks (= (sort all-disks) (range 1 (+ 1 (count all-disks))))
        ]
    (and (reduce #(and %1 %2) stack-order) correct-disks)))

(defn children-helper [state]
  (flatten
  (for [[current-peg current-disks] state]
    (if (not (empty? current-disks))
      (let [moving-disk (peek current-disks)]
        (map #(if (not (= (first %) current-peg))
                  (assoc state current-peg (pop current-disks) (first %) (conj (second %) moving-disk))
                  )
               state
               )
          )
        ))
    ))

(defn children [state]
  (filter #(and (not (= % nil))
                (legal? %))
          (children-helper state)))

(defn children [nothing])

 (defn cool-print-runnings [state]
;;   (println "The first peg is:" (:peg1 state))
;;   (println "The second peg is:" (:peg2 state))
;;   (println "The third peg is:" (:peg3 state))
   (println))
