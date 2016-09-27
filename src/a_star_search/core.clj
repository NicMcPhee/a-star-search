(ns a-star-search.core
  (:require [search.algorithms :as alg]
            [problems.n-puzzle :as np]
            [problems.peg-game :as pg])
  (:gen-class))

;;                   0
;;                 /   \
;;               1 ----- 2
;;             /   \   /   \
;;            3----- 4 ---- 5
;;          /  \   /   \  /   \
;;         6---- 7 ---- 8 ---- 9
;;       /  \  /  \   /  \   /   \
;;     10 -- 11 -- 12 --- 13 --- 14


;; (defn print-tree [board-state]
;;   (println "                  " (get board-state 0))
;;   (println "                 /   \\")
;;   (println "              "(get board-state 1)"-----"(get board-state 2))
;;   (println "             /   \\   /   \\")
;;   (println "           "(get board-state 3)"----"(get board-state 4)"----"(get board-state 5))
;;   (println "          /  \\   /   \\  /  \\")
;;   (println "        "(get board-state 6)"---"(get board-state 7)"----"(get board-state 8)"---"(get board-state 9))
;;   (println "       /  \\  /  \\   /  \\  /  \\")
;;   (println "     "(get board-state 10)"---"(get board-state 11)"---"(get board-state 12)"---"(get board-state 13)"---"(get board-state 14)))

(defn print-tree [board-state]
  (println board-state))


(defn print-results [result path costs goal-state]
  (println (str "We explored " (count result) " states."))
  (println "The path to the solution is:")
  (doseq [b path]
    (print-tree b)
    (println))
  (when costs
    (println "The path has" (count path) "steps and its cost is" (costs goal-state))))




;; (defn -main
;;   "Search for a hard-coded N-puzzle target state."
;;   [& args]
;;   (let [start-state (np/->State [[0 1 3] [4 2 5] [7 8 6]] [0 0])
;;         goal-state (np/->State [[0 1 2] [3 4 5] [6 7 8]] [0 0])
;;         max-states 1000000
;;         costs nil
;;         came-from (alg/breadth-first-search np/children max-states start-state goal-state)
;;         ; [came-from costs] (alg/shortest-path np/children (constantly 1)
;;         ;                                      max-states start-state goal-state )
;;         ; [came-from costs] (alg/shortest-path np/children np/prefer-horizontal-cost
;;         ;                                      max-states start-state goal-state)
;;         path (alg/extract-path came-from start-state goal-state)]
;;     (print-results came-from path costs goal-state)))

(def board-state [1 1 1 1 1 1 1 1 1 1 1 1 1 1 0])

(def full-board [1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1])

(defn rand-board [full-board]
  (assoc full-board (rand-int (count full-board)) 0))

(defn is-true [board-state]
  (= (reduce + board-state) 1))

;currently generates a board with a random index at 0
;if you want a specific board change board-state to your specific board and replace the first variable in the let with
;(let [start-state board-state

(defn -main
  "Search for a hard-coded N-puzzle target state."
  [& args]
  (let [start-state (rand-board full-board)
        goal-state is-true
        max-states 1000000
        costs nil
        [came-from final-state] (alg/breadth-first-search pg/children max-states start-state goal-state)
        ;[came-from costs final-state] (alg/shortest-path pg/children (constantly 1) max-states start-state goal-state)
        path (alg/extract-path came-from start-state final-state)]
    (print-results came-from path costs final-state)))

;; (defn -main
;;   [& args]
;;   (println pg/moves))


; Run (with timing) with
; (time (-main))
; or
; time lein run
