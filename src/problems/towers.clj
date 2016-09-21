(ns problems.towers)

(defrecord State [peg1 peg2 peg3])

(def start-state (->State [4 3 2 1] [] []))
(def goal-state (->State [] [4 3 2 1] []))

(defn legal? [new-state]
  (let [stack-order (and (if (< (count (:peg1 new-state)) 2)
                           true
                           (apply > (:peg1 new-state)))
                         (if (< (count (:peg2 new-state)) 2)
                           true
                           (apply > (:peg2 new-state)))
                         (if (< (count (:peg3 new-state)) 2)
                           true
                           (apply > (:peg3 new-state))))
        all-disks (concat (:peg1 new-state) (:peg2 new-state) (:peg3 new-state))
        correct-disks (= (sort all-disks) (range 1 (+ 1 (count all-disks))))]
    (and stack-order correct-disks)))

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

(defn cool-print-runnings [state]
  (println "The first peg is:" (:peg1 state))
  (println "The second peg is:" (:peg2 state))
  (println "The third peg is:" (:peg3 state))
  (println))
