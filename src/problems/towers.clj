(ns problems.towers)

(defrecord State [peh1 peh2 peh3])

(def start-state (->State [4 3 2 1] [] []))
(def goal-state (->State [] [4 3 2 1] []))

(defn legal? [new-state]
  (let [stack-order (and (if (< (count (:peh1 new-state)) 2)
                           true
                           (apply > (:peh1 new-state)))
                         (if (< (count (:peh2 new-state)) 2)
                           true
                           (apply > (:peh2 new-state)))
                         (if (< (count (:peh3 new-state)) 2)
                           true
                           (apply > (:peh3 new-state))))
        all-disks (concat (:peh1 new-state) (:peh2 new-state) (:peh3 new-state))
        correct-disks (= (sort all-disks) (range 1 (+ 1 (count all-disks))))]
    (and stack-order correct-disks)))

(defn children-helper [state]
  (flatten
  (for [[current-peh current-disks] state]
    (if (not (empty? current-disks))
      (let [moving-disk (peek current-disks)]
        (map #(if (not (= (first %) current-peh))
                  (assoc state current-peh (pop current-disks) (first %) (conj (second %) moving-disk))
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
  (println "The first peh is:" (:peh1 state))
  (println "The second peh is:" (:peh2 state))
  (println "The third peh is:" (:peh3 state))
  (println))
