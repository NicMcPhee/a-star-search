(ns problems.sudoku)


(defrecord State [board curr-position])


(def rows [y0 y1 y2 y3 y4 y5 y6 y7 y8])

(def cols [:x0 :x1 :x2 :x3 :x4 :x5 :x6 :x7 :x8])



;check row/column for duplicates
(defn duplicates? [coll]
       (not (apply distinct? (filter #(> % 0) coll))))


;check if a value/element is in a collection
(defn in? [coll value]
  (= true (some #(= value %) coll)))


;check if a row, column or 3x3 is legal
(defn legal? [coll]
  (and (not (duplicates? coll)) (= false (in? coll 0))))

(defn legal? [coll]
  (and (not (duplicates? coll)) (= false (in? coll 0))))

;test sequences

(def rows2 [0 0 3 3 5 6 7 8 9])

(def rows3 [3 1 3 4 5 9 7 8 6])

(def rows4 [3 1 2 4 5 9 7 8 6])


(not (duplicates? rows2))

(not (duplicates? rows3))

(not (duplicates? rows3))


(in? rows2 0)

(in? rows3 0)

(in? rows4 0)


(legal? rows2)

(legal? rows3)

(legal? rows4)


(def numBank (range 1 10))

;add to board functions
(defn add [board position number numBank]
  (let [num (nth numBank (- number 1))]
    (assoc-in board position num)))

;test add
(add [[0 1 2] [0 0 0] [0 0 0]
      [3 4 5] [0 0 0] [0 0 0]
      [6 7 8] [0 0 0] [0 0 0]

      [0 1 2] [0 0 0] [0 0 0]
      [3 4 5] [0 0 0] [0 0 0]
      [6 7 8] [0 0 0] [0 0 0]

      [0 1 2] [0 0 0] [0 0 0]
      [3 4 5] [0 0 0] [0 0 0]
      [6 7 8] [0 0 0] [0 0 0]] [0 0] 4 numBank)




(defn children [state]
  (let [x (first (:curr-position state))
        y (second (:curr-position state))
        board-size (count (:board state))]
  (for [[dx dy] [[-1, 0] [1, 0], [0, -1], [0, 1]]
        :let [new-x (+ x dx)
              new-y (+ y dy)]
        :when (legal? board-size new-x)
        :when (legal? board-size new-y)]
    (->State (swap (:board state) [x y] [new-x new-y])
             [new-x new-y]))))
