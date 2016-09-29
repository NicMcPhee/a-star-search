(ns problems.towers)

(defrecord State [vec-of-pegs])

(def start-state (->State [[3 2 1] [] []]))
(def goal-state (->State [[] [] [3 2 1]]))

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

;;;;;;; Helper Functions for Fitness ;;;;;;;
(defn abs [n] (max n (- n)))


(defn fitness-diff [goal-state current-state]
	(reduce + (map #(abs(- (count %2) (count %1))) (:vec-of-pegs goal-state) (:vec-of-pegs current-state)))
)

(defn fitness-lonely-disk [state]
	(count (filter #(= (count %) 1) (:vec-of-pegs state)))
)


;;
;; Determines the fitness of a state.
;; Takes in a state, wich we assume to be valid.
;; Returns an integer... ~~High numbers are "better"~~
;; According to Nic, higher numbers are worse...
;;
;; - point for each disk on the initial peg
;; - the cumulative difference in peg sizes over "one"...
;; - point for each peg with only one disk on it
;; - point for every "in use" peg beyond n+1 pegs
;;
(defn fitness [goal-state current-state]
	(+
	;;(fitness-initial-peg state)
	(- 1 (fitness-lonely-disk current-state))
	(fitness-diff goal-state current-state)
	)
)

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
	(filter legal?
	(flatten
	(for [peg-index (range 0 (count (:vec-of-pegs state)))]
		(children-by-peg peg-index state)
	)
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
