(ns problems.sudoku)

(defn do-children
  "Given a sudo state, a vector of ints, returns all valid child states as a vector of ints"
  [state]
 state)

(def children (memoize do-children))

(defn valid-state? [state]
  state)

(defn do-horizontal-valid? [state]
  (every? identity (map every-unique? (partition 9 state))))

(defn do-cube-valid? [n state]
  (let [infini-state (cycle state)
        cube [(nth (* n))]]
    (concat (nth))))

(def every-unique? (memoize do-every-unique?))
(defn do-every-unique?
  "Returns true if every element in the seq is unique or nil.
   Returns False otherwise."
  [a-list]
  (apply unique? a-list))

(def test-vec
  [nil nil nil nil nil nil nil 4 nil
   nil nil nil nil nil nil nil 4 nil])

(def test-vec-2
  [nil nil nil nil nil nil nil nil nil
   nil nil nil nil nil nil 4   4   nil])

(def test-vec-3 [1 2 3 4 5 6 7 8 9
                 1 2 3 4 5 6 7 8 9
                 1 2 3 4 5 6 7 8 9])

(def test-vec-4 [1 2 3 4 5 6 7 8 9
                 4 5 6 4 5 6 7 8 9
                 7 8 9 4 5 6 7 8 9])

(partition 27 test-vec-3)

(defn get-blocks [chunk]
  (map #(hop-block chunk %)))

(defn hop-block [chunk n]
  (let [adjusted-n (* n 3)]
    (into [] (concat (hop adjusted-n 0 chunk)
                     (hop adjusted-n 1 chunk)
                     (hop adjusted-n 2 chunk)))))

(defn hop [adjusted-n k chunk]
  (take-nth 9 (drop (+ adjusted-n k) chunk)))


(defn vertical-valid? [state]
  (every-vector-valid? (map #(do-vertical-valid? % state) (range 9)))
)

(defn do-vertical-valid? [coll state]
  (every-unique? (take-nth 9 (drop coll state)))
  )
