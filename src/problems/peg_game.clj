(ns problems.peg-game)

;go into file in terminal type "lein run"

;;                   0
;;                 /   \
;;               1 ----- 2
;;             /   \   /   \
;;            3----- 4 ---- 5
;;          /  \   /   \  /   \
;;         6---- 7 ---- 8 ---- 9
;;       /  \  /  \   /  \   /   \
;;     10 -- 11 -- 12 --- 13 --- 14



;; (def moves
;;   "All possble legal moves for the peg game."
;;   (hash-map 0 [[3 1] [5 2]]
;;             1 [[6 3] [8 4]]
;;             2 [[7 4] [9 5]]
;;             3 [[0 1] [5 4] [10 6] [12 7]]
;;             4 [[11 7] [13 8]]
;;             5 [[0 2] [3 4] [12 8] [14 9]]
;;             6 [[1 3] [8 7]]
;;             7 [[2 4] [9 8]]
;;             8 [[1 4] [6 7]]
;;             9 [[2 5] [7 8]]
;;             10 [[3 6] [12 11]]
;;             11 [[4 7] [13 12]]
;;             12 [[3 7] [5 8] [10 11] [14 13]]
;;             13 [[4 8] [11 12]]
;;             14 [[5 9] [12 13]]))


;;Diagonal increase left to right
;;[(+ (+ vector-index row-number) (+ row-number 1)) (+ vector-index row-number)]

;;Diagonal increase right to left
;;[(+ (+ vector-index (+ row-number 1)) (+ row-number 2)) (+ vector-index (+ row-number 1))]

;;Diagonal decrease left to right
;;[(- (- vector-index row-number) (- row-number 1)) (- vector-index row-number)]

;;Diagonal decrease right to left
;;[(- (- vector-index (- row-number 1)) (- row-number 2)) (- vector-index (- row-number 1))]

(defn num-rows [board]
  (loop [index 1
         total-index (count board)
         current-index 0
         number-of-rows 0]
    (if (not= total-index current-index)
      (recur (+ index 1) total-index (+ current-index index) (+ number-of-rows 1))
      number-of-rows)))


(defn generate-moves [board vector-index row-index row-number number-of-rows hashmap]
  [
    (if (> row-index 2) [(- vector-index 2) (- vector-index 1)])
    (if (< row-index (- row-number 1)) [(+ vector-index 2) (+ vector-index 1)])
    (if (> row-index 2) [(- (- vector-index row-number) (- row-number 1)) (- vector-index row-number)])
    (if (< row-index (- row-number 1)) [(- (- vector-index (- row-number 1)) (- row-number 2)) (- vector-index (- row-number 1))])
    (if (< row-number (- number-of-rows 1)) [(+ (+ vector-index (+ row-number 1)) (+ row-number 2)) (+ vector-index (+ row-number 1))])
    (if (< row-number (- number-of-rows 1)) [(+ (+ vector-index row-number) (+ row-number 1)) (+ vector-index row-number)])
  ])




(defn make-moves [board]
  (loop [board board
         vector-index 0
         row-index 1
         row-number 1
         number-of-rows (num-rows board)
         hashmap {}]
    (if (not= vector-index (count board))
      (if (not= row-index row-number)
        (recur board (+ vector-index 1) (+ row-index 1) row-number number-of-rows (assoc hashmap vector-index (generate-moves board vector-index row-index row-number number-of-rows hashmap)))
        (recur board (+ vector-index 1) 1 (+ row-number 1) number-of-rows (assoc hashmap vector-index (generate-moves board vector-index row-index row-number number-of-rows hashmap))))
      hashmap)))

(def moves
 (make-moves [1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1]))


(defn heuristic [parent child]
  (let [heuristic (get (frequencies (subvec child (quot (count child) 2) (count child))) 0)]
    (if (= heuristic nil)
      0
      (- 0 heuristic))))




(defn make-children
  "generates all possible children for the current empty space handed to it from the function children."
  [board-state index]
  (loop [children []
         moves (get moves index)
         board-state board-state
         i 0 ]
    (if (not= i (count moves))
      (if (and (= 1 (get board-state (get (get moves i) 0))) (= 1 (get board-state (get (get moves i) 1))))
        (recur (conj children (assoc board-state (get (get moves i) 0) 0 (get (get moves i) 1) 0 index 1)) moves board-state (inc i))
        (recur children moves board-state (inc i)))
      children)))




(defn children
  "looks through the entire board-state and finds empty spaces. It hands those spaces to make-children which generates all possible moves."
  [board-state]
  (loop [board-state board-state
         index 0
         children []]
    (if (not= index (count board-state))
      (if (= 0 (get board-state index))
        (recur board-state (inc index) (concat children (make-children board-state index)))
        (recur board-state (inc index) children))
      children)))



