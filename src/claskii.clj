(ns claskii
  (:import (java.awt Color RenderingHints)
	   (java.awt.image BufferedImage)
	   (javax.swing ImageIcon)
	   (javax.imageio ImageIO)
	   (java.io File))
  (:require [compojure.core :refer :all]
            [hiccup.core :refer :all]))

(def ascii-chars [\# \A \@ \% \$ \+ \= \* \: \, \. \space])

(defmacro get-properties [obj & properties]
  (let [target (gensym)]
    `(let [~target ~obj]
       (vector ~@(for [property properties]
		   `(~property ~target))))))

(defn scale-image [uri new-width]
  (let [image        (-> (ImageIcon. uri) .getImage)
	new-height   (* (/ new-width (.getWidth image)) (.getHeight image))
	scaled-image (BufferedImage. new-width new-height
				     BufferedImage/TYPE_INT_RGB)
	gfx2d        (doto (.createGraphics scaled-image)
		       (.setRenderingHint RenderingHints/KEY_INTERPOLATION
					  RenderingHints/VALUE_INTERPOLATION_BILINEAR)
		       (.drawImage image 0 0 new-width new-height nil)
		       .dispose)]
    scaled-image))

(defn ascii [img x y color?]
  (let [[red green blue] (get-properties ( Color. (.getRGB img x y))
					 .getRed .getGreen .getBlue)
	peak    (apply max [red green blue])
	idx     (if (zero? peak)
		  (dec (count ascii-chars))
		  (dec (int (+ 1/2 (* (count ascii-chars) (/ peak 255))))))
	output  (nth ascii-chars (if (pos? idx) idx 0))	]
    (if color?
      (html [:span {:style (format "color: rgb(%s,%s,%s);" red green blue)} output])
      output)))

(defn convert-image [uri w color?]
  (let [raw-image   (scale-image uri w)
	ascii-image (->> (for [y (range (.getHeight raw-image))
			       x (range (.getWidth  raw-image))]
			   (ascii raw-image x y color?))
			 (partition w))
	output      (->> ascii-image
			 (interpose (if color? "<BR/>" \newline))
			 flatten)]
    (if color?
      (html [:pre {:style "font-size:5pt; letter-spacing:1px;
                           line-height:4pt; font-weight:bold;"}
	     output])
      (println output))))
