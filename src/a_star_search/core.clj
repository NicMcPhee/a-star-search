(ns a-star-search.core
  (:require [search.algorithms :as alg]
            [problems.richardsmitchellettesudoku :as rmsd])
  (:gen-class))

(defn print-results [result path costs goal-state]
  (println (str "We explored " (count result) " states."))
  (println "The path to the solution is:")
  (doseq [b (map :board path)]
    (println b))
  (when costs
    (println "The path has" (count path) "steps and its cost is" (costs goal-state))))

(defn -main
  "Search for a hard-coded Sudoku target state."
  [& args]
  (let [start-state (rmsd/->State [[[[0 0 6][0 0 0][2 0 0]]
                                    [[0 8 0][2 5 9][6 0 7]]
                                    [[9 0 0][0 0 0][0 0 5]]]
                                   [[[0 9 8][5 2 0][0 7 1]]
                                    [[0 0 0][0 0 0][0 0 0]]
                                    [[7 5 0][0 6 9][2 4 0]]]
                                   [[[3 0 0][0 0 0][0 0 2]]
                                    [[8 0 1][4 2 6][0 9 0]]
                                    [[0 0 2][0 0 0][6 0 0]]]])
        goal-state (rmsd/->State [[[[1 5 6][8 3 7][2 4 9]]
                                   [[3 8 4][2 5 9][6 1 7]]
                                   [[9 2 7][4 1 6][8 3 5]]]
                                  [[[4 9 8][5 2 3][6 7 1]]
                                   [[1 6 2][7 4 8][9 3 5]]
                                   [[7 5 3][1 6 9][2 4 8]]]
                                  [[[3 6 4][9 8 5][7 1 2]]
                                   [[8 7 1][4 2 6][5 9 3]]
                                   [[5 9 2][3 7 1][6 8 4]]]])
        max-states 1000000
        costs nil
        came-from (alg/breadth-first-search rmsd/children max-states start-state goal-state)
        ; [came-from costs] (alg/shortest-path rmsd/children (constantly 1)
        ;                                      max-states start-state goal-state )
        ; [came-from costs] (alg/shortest-path rmsd/children rmsd/prefer-horizontal-cost
        ;                                      max-states start-state goal-state)
        path (alg/extract-path came-from start-state goal-state)]
    (print-results came-from path costs goal-state)))

; Run (with timing) with
; (time (-main))
; or
; time lein run
