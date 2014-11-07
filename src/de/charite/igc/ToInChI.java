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
 * A class that holds methods to convert other formats into InChI and InChIKey.
 *
 * @author Vishal Siramshetty <vishal-babu.siramshetty[at]charite.de>
 */
public class ToInChI {

    /**
     * Returns an InChI string when provided with a MOL file as string.
     *
     * @param mol
     * @return InChI
     */
    public static String getInChIFromMol(String mol) {
        String result = null;
        try {
            String urlString = "http://www.chemspider.com/InChI.asmx/MolToInChI?mol=" + mol;
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
     * Returns an InChIKey as string when provided with a MOL file as string.
     *
     * @param mol
     * @return InChIKey
     */
    public static String getInChIKeyFromMol(String mol) {
        String result = null;
        try {
            String urlString = "http://www.chemspider.com/InChI.asmx/MolToInChIKey?mol=" + mol;
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
     * Returns an InChI as string when provided with a SMILES string.
     *
     * @param smiles
     * @return InChI
     */
    public static String getInChIFromSMILES(String smiles) {
        String result = null;
        try {
            String urlString = "http://www.chemspider.com/InChI.asmx/SMILESToInChI?smiles=" + smiles;
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
     * Returns an InChIKey as string when provided with a SMILES string.
     *
     * @param smiles
     * @return InChIKey
     */
    public static String getInChIKeyFromSMILES(String smiles) {

        String inchi = getInChIFromSMILES(smiles);
        String inchikey = FromInChI.getInChIKeyFromInChI(inchi);

        return inchikey;
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

}
