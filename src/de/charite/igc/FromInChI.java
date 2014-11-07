/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.charite.igc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

/**
 *
 * A class that holds methods to convert InChI and InChIKey to other formats.
 *
 * @author Vishal Siramshetty <vishal-babu.siramshetty[at]charite.de>
 */
public class FromInChI {

    /**
     * Returns a SMILES string when provided with an InChIKey string.
     *
     * @param inchikey
     * @return SMILES
     * @throws de.charite.igc.FromInChI.InChIException
     */
    public static String getSMILESFromInChIKey(String inchikey) throws InChIException {

        boolean valid = isValidInChIKey(inchikey);
        if (!valid) {
            throw new InChIException("Invalid InChIKey provided.");
        } else {
            String inchi = getInChIFromInChIKey(inchikey);
            String smiles = getSMILESFromInChI(inchi);
            return smiles;
        }
    }

    /**
     * Returns an InChI string when provided with an InChIKey string.
     *
     * @param inchiKey
     * @return InChI
     */
    public static String getInChIFromInChIKey(String inchiKey) {
        String result = null;
        try {
            String urlString = "http://www.chemspider.com/InChI.asmx/InChIKeyToInChI?inchi_key=" + inchiKey;
            URL url = new URL(urlString);
            HttpURLConnection conn = getConnection(url);
            result = getResponse(conn);
            result = getElementByID(result, "string");

        } catch (MalformedURLException ex) {
            Logger.getLogger(FromInChI.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    /**
     * Returns a MOL file as string when provided with an InChIKey string.
     *
     * @param inchiKey
     * @return MOL file as string
     */
    public static String getMolFromInChIKey(String inchiKey) {
        String result = null;
        try {
            String urlString = "http://www.chemspider.com/InChI.asmx/InChIKeyToMol?inchi_key=" + inchiKey;
            URL url = new URL(urlString);
            HttpURLConnection conn = getConnection(url);
            result = getResponse(conn);
            result = getElementByID(result, "string");

        } catch (MalformedURLException ex) {
            Logger.getLogger(FromInChI.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    /**
     * Returns an InChIKey string when provided with an InChI string.
     *
     * @param inchi
     * @return InChIKey
     */
    public static String getInChIKeyFromInChI(String inchi) {
        String result = null;
        try {
            String urlString = "http://www.chemspider.com/InChI.asmx/InChIToInChIKey?inchi=" + inchi;
            URL url = new URL(urlString);
            HttpURLConnection conn = getConnection(url);
            result = getResponse(conn);
            result = getElementByID(result, "string");

        } catch (MalformedURLException ex) {
            Logger.getLogger(FromInChI.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    /**
     * Returns a SMILES string when provided with an InChI string.
     *
     * @param inchi
     * @return SMILES
     */
    public static String getSMILESFromInChI(String inchi) {
        String result = null;
        try {
            String urlString = "http://www.chemspider.com/InChI.asmx/InChIToSMILES?inchi=" + inchi;
            URL url = new URL(urlString);
            HttpURLConnection conn = getConnection(url);
            result = getResponse(conn);
            result = getElementByID(result, "string");

        } catch (MalformedURLException ex) {
            Logger.getLogger(FromInChI.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    /**
     * Returns a MOL file as string when provided with an InChI string.
     *
     * @param inchi
     * @return MOL file as string
     */
    public static String getMolFromInChI(String inchi) {
        String result = null;
        try {
            String urlString = "http://www.chemspider.com/InChI.asmx/InChIToMol?inchi=" + inchi;
            URL url = new URL(urlString);
            HttpURLConnection conn = getConnection(url);
            result = getResponse(conn);
            result = getElementByID(result, "string");

        } catch (MalformedURLException ex) {
            Logger.getLogger(FromInChI.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    /**
     * Returns true if a provided InChIKey is valid.
     *
     * @param inchiKey
     * @return true or false
     */
    public static boolean isValidInChIKey(String inchiKey) {
        boolean result = false;
        try {
            String urlString = "http://www.chemspider.com/InChI.asmx/IsValidInChIKey?inchi_key=" + inchiKey;
            URL url = new URL(urlString);
            HttpURLConnection conn = getConnection(url);
            String res = getResponse(conn);
            res = getElementByID(res, "boolean");
            if (res.equals("true")) {
                result = true;
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(FromInChI.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    // private methods for local use
    private static String getElementByID(String result, String id) {

        if (result != null) {
            Document doc = Jsoup.parse(result, "", Parser.xmlParser());
            return doc.select(id).text();
        }

        return null;
    }

    private static HttpURLConnection getConnection(URL url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "JavaClient/1.0");
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
        } catch (IOException ex) {
            Logger.getLogger(FromInChI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;

    }

    private static String getResponse(HttpURLConnection conn) {
        String response = null;
        try {
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuilder resp;
                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()))) {
                    String inputLine;
                    resp = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        resp.append(inputLine);
                    }
                }
                response = resp.toString();
            }

        } catch (IOException ex) {
            Logger.getLogger(FromInChI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    static class InChIException extends Exception {

        public InChIException(String message) {
            super("InChI Exception: " + message);
        }

        public InChIException(String message, Throwable cause) {
            super("InChI Exception: " + message, cause);
        }

        @Override
        public String toString() {
            return getMessage() + " " + getCause();
        }
    }

}
