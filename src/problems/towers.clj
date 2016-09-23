(ns problems.towers)

(defrecord State [vec-of-pegs])

(def start-state (->State [[4 3 2 1] [] [] [] []]))
(def goal-state (->State [[] [] [] [] [4 3 2 1]]))

;;
;; Determines whether or not a given state is valid
;; Checks that no disks are out of order
;; Checks that no disks are duplicated
;; 
;; Returns a boolean
;;
(defn legal? [new-state]
  (let [stack-order (and (map #(if (< (count %) 2)
                                        true
                                        (apply > %)) (:vec-of-pegs new-state)))

        all-disks (apply concat (:vec-of-pegs new-state))
        correct-disks (= (sort all-disks) (range 1 (+ 1 (count all-disks))))
        ]
    (and (reduce #(and %1 %2) stack-order) correct-disks)))

;;
;; Helper function to generate children
;;
;; Generates a set of likely-valid children by moving the
;; top disk of a given peg. Does not include a copy of the parent
;; state in the list of children returned.
;;
;; Returns a list of states
;;
(defn children-by-peg [peg-index state]
(flatten
(filter #(not (= % nil))
(let [all-pegs (:vec-of-pegs state)
	peg (all-pegs peg-index)]
	
(if (not (empty? peg))
   (let [moving-disk (peek peg)]
	(for [secondary-index (range 0 (count all-pegs))]
		(if (not (= secondary-index peg-index))
			(->State (assoc all-pegs
				peg-index (pop peg)
				secondary-index (conj (all-pegs secondary-index) moving-disk)))
		)
	)
    )
)))))

;;
;; Generates all the potentially valid children given a state
;;
;; Returns a list of states
;; 
(defn children [state]
	(flatten
	(for [peg-index (range 0 (count (:vec-of-pegs state)))]
		(children-by-peg peg-index state)
	)
	)
)

;; 
;; Given a state, prints it out in a more readable format. 
;;
(defn cool-print-runnings [state]
	(doseq [peg-num (range 0 (count (:vec-of-pegs state)))]
		(println "Peg #" peg-num " -> " ((:vec-of-pegs state) peg-num))
	)
	(println)
)
